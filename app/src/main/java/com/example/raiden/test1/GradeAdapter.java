package com.example.raiden.test1;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {
    private List<CourseInfo> mCourseList = new ArrayList<>();
    private static final String TAG = "GradeAdapter";

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView grade;
        View entireView;
        ProgressBar courseProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            entireView = itemView;
            title = (TextView) itemView.findViewById(R.id.grade_slot_title);
            grade = (TextView) itemView.findViewById(R.id.grade_slot_grade);
            courseProgress = (ProgressBar) itemView.findViewById(R.id.grade_slot_progress);
        }
    }

    public GradeAdapter(Context mContext) {
        SharedPreferences data = mContext.getSharedPreferences("MyCourses",Context.MODE_PRIVATE);

        Map<String,?> allCourses = data.getAll();
        for(Map.Entry<String,?> entry: allCourses.entrySet()) {
            Gson gson = new Gson();
            mCourseList.add(gson.fromJson(entry.getValue().toString(),CourseInfo.class));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grade_slot,viewGroup,false);
        ViewHolder mViewHolder = new ViewHolder(view);
        mViewHolder.entireView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),courseDetail.class);
                view.getContext().startActivity(intent);
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseInfo course = mCourseList.get(position);
        holder.title.setText(course.getCourseName());
        holder.grade.setText(course.getGrade()+"%");

        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(holder.courseProgress, "progress", 0, course.getGrade());
        progressAnimator.setDuration(600);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
        holder.courseProgress.setTag(progressAnimator);
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }
}

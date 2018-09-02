package com.example.raiden.test1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ViewHolder> {
    private List<courseComponent> mEventList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventname;
        TextView eventscore;
        TextView eventweight;
        TextView eventresult;
        View entireview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventname = (TextView) itemView.findViewById(R.id.Component_name);
            eventscore = (TextView) itemView.findViewById(R.id.Component_score);
            eventresult = (TextView) itemView.findViewById(R.id.Component_result);
            eventweight = (TextView) itemView.findViewById(R.id.Component_weight);
            entireview = itemView;
        }
    }

    public ComponentAdapter(Context mContext,String coursetitle) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyCourses",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String rawdata = sharedPreferences.getString(coursetitle,"NULL");
        CourseInfo mCourse = gson.fromJson(rawdata,CourseInfo.class);
        mEventList = mCourse.getCourseData();
        Collections.sort(mEventList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.component_slot,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        courseComponent event = mEventList.get(i);
        viewHolder.eventname.setText(event.getName());
        viewHolder.eventweight.setText(event.getWeight()+"");
        viewHolder.eventscore.setText(event.getScore()+"");

        String result = String.format("%.2f",event.getWeight() * event.getScore() * 0.01);
        viewHolder.eventresult.setText(result);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }
}

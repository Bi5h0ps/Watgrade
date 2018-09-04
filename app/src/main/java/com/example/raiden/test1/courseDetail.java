package com.example.raiden.test1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.shinelw.library.ColorArcProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

public class courseDetail extends AppCompatActivity {
    @BindView(R.id.grade_progress)
    public ColorArcProgressBar progressBar;
    @BindView(R.id.Course_component_display)
    RecyclerView mComponentDisplay;

    @BindView(R.id.Add_new_event)
    public FancyButton newevent;

    private String coursetitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        coursetitle = getIntent().getStringExtra("Coursename");
        progressBar.setUnit(coursetitle);

        SharedPreferences sharedPreferences = getSharedPreferences("MyCourses", MODE_PRIVATE);
        String coursedata = sharedPreferences.getString(coursetitle, "Null");
        Gson gson = new Gson();
        CourseInfo mCourseInfo = gson.fromJson(coursedata, CourseInfo.class);
        progressBar.setCurrentValues(mCourseInfo.getGrade());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mComponentDisplay.setLayoutManager(linearLayoutManager);
        ComponentAdapter adapter = new ComponentAdapter(this,coursetitle);
        mComponentDisplay.setAdapter(adapter);
    }

    @OnClick(R.id.Add_new_event)
    public void onNewEvent() {
        Intent intent = new Intent(this,Add_course_component.class);
        intent.putExtra("CourseTitle",coursetitle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ComponentAdapter adapter = new ComponentAdapter(this,coursetitle);
        mComponentDisplay.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MyCourses", MODE_PRIVATE);
        String coursedata = sharedPreferences.getString(coursetitle, "Null");
        Gson gson = new Gson();
        CourseInfo mCourseInfo = gson.fromJson(coursedata, CourseInfo.class);
        progressBar.setCurrentValues(mCourseInfo.getGrade());
    }
}

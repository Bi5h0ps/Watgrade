package com.example.raiden.test1;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.shinelw.library.ColorArcProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class courseDetail extends AppCompatActivity {
    @BindView(R.id.grade_progress)
    public ColorArcProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        String coursetitle = getIntent().getStringExtra("Coursename");
        progressBar.setUnit(coursetitle);

        SharedPreferences sharedPreferences = getSharedPreferences("MyCourses",MODE_PRIVATE);
        String coursedata = sharedPreferences.getString(coursetitle,"Null");
        Gson gson = new Gson();
        CourseInfo mCourseInfo = gson.fromJson(coursedata,CourseInfo.class);
        progressBar.setCurrentValues(mCourseInfo.getGrade());
}
}

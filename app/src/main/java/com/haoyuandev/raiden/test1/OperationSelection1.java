package com.haoyuandev.raiden.test1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.haoyuandev.raiden.test1.R;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OperationSelection1 extends AppCompatActivity {

    @BindView(R.id.edit_component)
    Button mEditButton;
    @BindView(R.id.delete_component)
    Button mDeleteButton;
    @BindView(R.id.custom_navi_title)
    TextView mActivityTitle;
    @BindView(R.id.confirm_activity)
    TextView mConfrimButton;

    private String CourseTitle;
    private String EventName;

    private CourseInfo mCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_selection1);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mConfrimButton.setVisibility(View.INVISIBLE);
        mActivityTitle.setText("Options");

        CourseTitle = getIntent().getStringExtra("CourseTitle");
        EventName = getIntent().getStringExtra("EventName");
    }

    @OnClick(R.id.edit_component)
    public void OnEdit() {
        Intent intent = new Intent(this,EditCourseComponent.class);
        intent.putExtra("CourseTitle",CourseTitle);
        intent.putExtra("EventName",EventName);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.delete_component)
    public void OnDelete() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyCourses", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        mCourse = gson.fromJson(sharedPreferences.getString(CourseTitle, "Null"), CourseInfo.class);

        List<courseComponent> data = mCourse.getCourseData();
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).getName().equals(EventName)) {
                data.remove(i);
            }
        }

        String updated = gson.toJson(mCourse);
        editor.putString(CourseTitle, updated);
        editor.apply();

        finish();
    }
}

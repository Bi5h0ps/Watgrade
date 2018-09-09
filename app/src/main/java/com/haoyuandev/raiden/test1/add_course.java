package com.haoyuandev.raiden.test1;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haoyuandev.raiden.test1.R;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class add_course extends AppCompatActivity {
    @BindView(R.id.custom_navi_title)
    TextView tvTitle;

    @BindView(R.id.cancel_activity)
    ImageView tvBack;

    @BindView(R.id.confirm_activity)
    TextView tvNext;

    @BindView(R.id.new_course_name)
    EditText tvCourseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        tvTitle.setText("Add New Course");
    }

    @OnClick(R.id.cancel_activity)
    public void Clicked() {
        this.finish();
    }

    @OnClick(R.id.confirm_activity)
    public void confrimActivity() {
        String courseName = tvCourseName.getText().toString();
        if(courseName.isEmpty()) {
            Toast.makeText(this,"Please provide the course code.",Toast.LENGTH_LONG).show();
        } else {
            CourseInfo course = new CourseInfo(courseName);
            Gson gson = new Gson();

            SharedPreferences sharedPreferences = getSharedPreferences("MyCourses",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(courseName,gson.toJson(course));
            editor.apply();
            this.finish();
        }
    }
}

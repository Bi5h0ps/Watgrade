package com.example.raiden.test1;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Add_course_component extends AppCompatActivity {
    @BindView(R.id.custom_navi_title)
    TextView mTitle;
    @BindView(R.id.confirm_activity)
    TextView mConfrim;
    @BindView(R.id.new_component_name)
    EditText mComponentName;
    @BindView(R.id.new_component_weight)
    EditText mComponentWeight;
    @BindView(R.id.new_component_score)
    EditText mComponentScore;

    String coursetitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_component);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mTitle.setText("New Course Event");
        coursetitle = getIntent().getStringExtra("CourseTitle");
    }

    @OnClick(R.id.confirm_activity)
    public void onConfrim() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyCourses",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        CourseInfo mCourse = gson.fromJson(sharedPreferences.getString(coursetitle,"Null"),CourseInfo.class);
        String eventname = mComponentName.getText().toString();
        Double eventweight = Double.parseDouble(mComponentWeight.getText().toString());
        Double eventscore = Double.parseDouble(mComponentScore.getText().toString());
        if(eventname.isEmpty()) {
            Toast.makeText(this,"Event name must not be empty",Toast.LENGTH_SHORT).show();
        } else {
            if(eventweight > 100 || eventweight < 0 || eventweight.isNaN()) {
                Toast.makeText(this,"Event weight not applicable",Toast.LENGTH_SHORT).show();
            } else {
                if (eventscore > 100 || eventscore < 0 || eventscore.isNaN()) {
                    Toast.makeText(this,"Event score not applicable",Toast.LENGTH_SHORT).show();
                } else {
                    courseComponent mCourseComponent = new courseComponent(eventname,eventweight,eventscore);
                    mCourse.addCourseData(mCourseComponent);
                    String updated = gson.toJson(mCourse);
                    editor.putString(coursetitle,updated);
                    editor.apply();
                    finish();
                }
            }
        }
    }
}

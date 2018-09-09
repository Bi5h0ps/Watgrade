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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditCourseComponent extends AppCompatActivity {

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
    @BindView(R.id.cancel_activity)
    ImageView mCancel;

    private String coursetitle;
    private String eventname;
    private CourseInfo mCourse;
    private courseComponent mCourseComponent;
    private double origionalweight;

    private static final String TAG = "Add_course_component";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_component);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mTitle.setText("Edit Course Event");
        mConfrim.setText("Save");
        coursetitle = getIntent().getStringExtra("CourseTitle");
        eventname = getIntent().getStringExtra("EventName");

        SharedPreferences sharedPreferences = getSharedPreferences("MyCourses", MODE_PRIVATE);
        Gson gson = new Gson();
        mCourse = gson.fromJson(sharedPreferences.getString(coursetitle, "Null"), CourseInfo.class);

        List<courseComponent> data = mCourse.getCourseData();
        mCourseComponent = data.get(0);
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).getName().equals(eventname)) {
                mCourseComponent = data.get(i);
            }
        }

        mComponentName.setText(mCourseComponent.getName());
        mComponentScore.setText(mCourseComponent.getScore() + "");
        mComponentWeight.setText(mCourseComponent.getWeight() + "");
        origionalweight = mCourseComponent.getWeight();
    }

    @OnClick(R.id.cancel_activity)
    public void onCancel() {
        finish();
    }

    @OnClick(R.id.confirm_activity)
    public void onConfrim() {
        String eventname = mComponentName.getText().toString();
        String rawweight = mComponentWeight.getText().toString();
        String rawscore = mComponentScore.getText().toString();
        if (eventname.isEmpty()) {
            Toast.makeText(this, "Event name must not be empty", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (eventname.length() > 15) {
                Toast.makeText(this, "Event name must not be less than 15 characters", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (rawweight.isEmpty()) {
            Toast.makeText(this, "Event weight must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rawscore.isEmpty()) {
            Toast.makeText(this, "Event score must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Double eventweight = Double.parseDouble(rawweight);
        Double eventscore = Double.parseDouble(rawscore);

        if (eventweight > 100 || eventweight < 0) {
            Toast.makeText(this, "Event weight not applicable", Toast.LENGTH_SHORT).show();
            return;
        }

        if ((100 - mCourse.getWeight() + origionalweight) < eventweight) {
            Toast.makeText(this,"Net weight exceeded 100%! Current weight avaliable: " + (100 - mCourse.getWeight()) + "%",Toast.LENGTH_SHORT).show();
            return;
        }

        if (eventscore > 100 || eventscore < 0) {
            Toast.makeText(this, "Event score not applicable", Toast.LENGTH_SHORT).show();
            return;
        }

        mCourseComponent.setName(eventname);
        mCourseComponent.setScore(eventscore);
        mCourseComponent.setWeight(eventweight);


        SharedPreferences sharedPreferences = getSharedPreferences("MyCourses", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        String updated = gson.toJson(mCourse);
        editor.putString(coursetitle, updated);
        editor.apply();
        finish();
    }
}

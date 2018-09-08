package com.example.raiden.test1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.gson.Gson;
import com.shinelw.library.ColorArcProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

public class courseDetail extends AppCompatActivity {
    @BindView(R.id.grade_progress)
    ColorArcProgressBar progressBar;
    @BindView(R.id.Course_component_display)
    RecyclerView mComponentDisplay;

    @BindView(R.id.Add_new_event)
    FancyButton newevent;
    @BindView(R.id.cancel_activity)
    ImageView ActivityCancel;
    @BindView(R.id.confirm_activity)
    TextView ActivityConfrim;
    @BindView(R.id.custom_navi_title)
    TextView ActivityTitle;

    private String coursetitle;
    private static final String TAG = "courseDetail";

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
        ActivityConfrim.setVisibility(View.INVISIBLE);
        ActivityTitle.setText(coursetitle);

        SharedPreferences instructionStep = getSharedPreferences("ins",MODE_PRIVATE);
        SharedPreferences.Editor instructionEdit = instructionStep.edit();
        if(!instructionStep.getBoolean("step2",false)) {
            final SpannableString note1 = new SpannableString("Holding to edit the course event");
            final SpannableString note2 = new SpannableString("Add a new course event by clicking the \"Add Event\" button");

            final TapTargetSequence sequence = new TapTargetSequence(this)
                    .targets(
                            TapTarget.forView(mComponentDisplay,note1)
                                    .outerCircleColor(R.color.background_color_gray)
                                    //.outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                                    .titleTextSize(30)                  // Specify the size (in sp) of the title text
                                    .titleTextColor(R.color.background_color_black)      // Specify the color of the title text
                                    .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                                    .descriptionTextColor(R.color.background_color_black)  // Specify the color of the description text
                                    .textColor(R.color.background_color_black)            // Specify a color for both the title and description text
                                    .drawShadow(true)                   // Whether to draw a drop shadow or not
                                    .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                    .tintTarget(true)                   // Whether to tint the target view's color
                                    .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                                    .targetRadius(40)
                                    .id(1),

                            TapTarget.forView(newevent,note2)
                                    .outerCircleColor(R.color.background_color_gray)
                                    //.outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                                    .titleTextSize(30)                  // Specify the size (in sp) of the title text
                                    .titleTextColor(R.color.background_color_black)      // Specify the color of the title text
                                    .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                                    .descriptionTextColor(R.color.background_color_black)  // Specify the color of the description text
                                    .textColor(R.color.background_color_black)            // Specify a color for both the title and description text
                                    .drawShadow(true)                   // Whether to draw a drop shadow or not
                                    .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                                    .tintTarget(true)                   // Whether to tint the target view's color
                                    .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                                    .targetRadius(35)
                                    .id(2));

            sequence.start();
            instructionEdit.putBoolean("step2",true);
            instructionEdit.apply();
        }
    }

    @OnClick(R.id.cancel_activity)
    public  void onCancel() {
        finish();
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

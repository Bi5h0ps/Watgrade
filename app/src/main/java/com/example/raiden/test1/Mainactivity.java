package com.example.raiden.test1;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import me.wangyuwei.particleview.Particle;
import me.wangyuwei.particleview.ParticleView;

public class Mainactivity extends AppCompatActivity {

    private RecyclerView mRecycle;
    private GradeAdapter adapter;
    public static RefreshLayout refreshLayout;
    private static final String TAG = "Mainactivity";
    private FloatingActionButton fab;
    TextView tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Watgrade");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(getDrawable(R.drawable.business_add));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mainactivity.this,add_course.class);
                startActivity(intent);
            }
        });

        refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                adapter = new GradeAdapter(getApplicationContext());
                mRecycle.setAdapter(adapter);
                refreshlayout.finishRefresh(200);//传入false表示刷新失败
            }
        });

        CourseInfo courseInfo = new CourseInfo("Orientation");
        courseInfo.addCourseData(new courseComponent("Assignment0",100,99));
        Gson gson = new Gson();
        String data = gson.toJson(courseInfo);
        SharedPreferences database = getSharedPreferences("MyCourses",MODE_PRIVATE);
        SharedPreferences.Editor databaseeditor = database.edit();
        databaseeditor.putString(courseInfo.getCourseName(),data);
        databaseeditor.apply();

        mRecycle = (RecyclerView) findViewById(R.id.course_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycle.setLayoutManager(manager);
        adapter = new GradeAdapter(getApplicationContext());
        mRecycle.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        SharedPreferences sharedPreferences = getSharedPreferences("ins",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!sharedPreferences.getBoolean("step1",false)) {
            final SpannableString note1 = new SpannableString("Press \"+\" button to add a new course");
            final SpannableString note2 = new SpannableString("Tap to view the course detail, holding to delete the course");

            final TapTargetSequence sequence = new TapTargetSequence(this)
                    .targets(
            TapTarget.forView(fab,note1)
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

                            TapTarget.forView(mRecycle,note2)
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
                                    .targetRadius(35)
                                    .id(2));

            sequence.start();
            editor.putBoolean("step1",true);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView.LayoutManager manager = mRecycle.getLayoutManager();
        for(int i = 0; i < manager.getChildCount(); i++) {
            View view = mRecycle.getChildAt(i);
            ObjectAnimator animator = (ObjectAnimator)view.findViewById(R.id.grade_slot_progress).getTag();
            animator.start();
        }
        adapter = new GradeAdapter(getApplicationContext());
        mRecycle.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

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
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

        initCourseList();
        mRecycle = (RecyclerView) findViewById(R.id.course_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycle.setLayoutManager(manager);
        adapter = new GradeAdapter(getApplicationContext());
        mRecycle.setAdapter(adapter);
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

    private void initCourseList() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyCourses",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        CourseInfo course0 = new CourseInfo("CS246");
        course0.addCourseData(new courseComponent("Final",100,84));
        CourseInfo course1 = new CourseInfo("CS245");
        course1.addCourseData(new courseComponent("Final",100,79));
        CourseInfo course2 = new CourseInfo("ECON102");
        course2.addCourseData(new courseComponent("Final",100,84));
        CourseInfo course3 = new CourseInfo("Stat231");
        course3.addCourseData(new courseComponent("Final",100,86));
        CourseInfo course4 = new CourseInfo("CS240");
        course4.addCourseData(new courseComponent("Final",100,92));
        CourseInfo course5 = new CourseInfo("CS241");
        course5.addCourseData(new courseComponent("Final",100,94));
        CourseInfo course6 = new CourseInfo("CS251");
        course6.addCourseData(new courseComponent("Final",100,97));
        CourseInfo course7 = new CourseInfo("Math239");
        course7.addCourseData(new courseComponent("Final",100,77));
        CourseInfo course8 = new CourseInfo("PD999");

        Gson gson = new Gson();
        editor.putString(course0.getCourseName(),gson.toJson(course0));
        editor.putString(course1.getCourseName(),gson.toJson(course1));
        editor.putString(course2.getCourseName(),gson.toJson(course2));
        editor.putString(course3.getCourseName(),gson.toJson(course3));
        editor.putString(course4.getCourseName(),gson.toJson(course4));
        editor.putString(course5.getCourseName(),gson.toJson(course5));
        editor.putString(course6.getCourseName(),gson.toJson(course6));
        editor.putString(course7.getCourseName(),gson.toJson(course7));
        editor.putString(course8.getCourseName(),gson.toJson(course8));
        editor.apply();
        /*
        enrolledCourses.add(course0);
        enrolledCourses.add(course1);
        enrolledCourses.add(course2);
        enrolledCourses.add(course3);
        enrolledCourses.add(course4);
        enrolledCourses.add(course5);
        enrolledCourses.add(course6);
        enrolledCourses.add(course7);
        enrolledCourses.add(course8);*/
    }
}

package com.example.raiden.test1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class add_course extends AppCompatActivity {
/*whatsup*/
    @BindView(R.id.custom_navi_title)
    TextView tvTitle;

    @BindView(R.id.cancel_activity)
    ImageView tvBack;

    @BindView(R.id.confirm_activity)
    TextView tvNext;

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
}

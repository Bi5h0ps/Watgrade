package com.haoyuandev.raiden.test1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.haoyuandev.raiden.test1.R;

import me.wangyuwei.particleview.ParticleView;

public class LauncherAnimation extends AppCompatActivity {
    ParticleView mParticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_animation);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mParticle = (ParticleView) findViewById(R.id.my_particle_animation);
        mParticle.startAnim();

        mParticle.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                Intent intent = new Intent(LauncherAnimation.this,Mainactivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

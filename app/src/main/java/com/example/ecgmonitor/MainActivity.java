package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.ecgmonitor.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;

    Animation topAnimantion,bottomAnimation,middleAnimation, zoomInAnimation, zoomOutAnimation, leftAnimation, rightAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        topAnimantion = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);
        zoomInAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_animation);
        zoomOutAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_out_animation);

        leftAnimation = AnimationUtils.loadAnimation(this, R.anim.left_animation);
        rightAnimation = AnimationUtils.loadAnimation(this, R.anim.right_animation);

        activityMainBinding.imageView.setAnimation(zoomInAnimation);
        activityMainBinding.nslTxt.setAnimation(leftAnimation);
        activityMainBinding.hubTxt.setAnimation(rightAnimation);
        activityMainBinding.poweredTxt.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, BoardingActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);

    }
}
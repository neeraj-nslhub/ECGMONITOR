package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ecgmonitor.databinding.ActivityBoardingBinding;

public class BoardingActivity extends AppCompatActivity {


    ActivityBoardingBinding activityBoardingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityBoardingBinding = ActivityBoardingBinding.inflate(getLayoutInflater());
        setContentView(activityBoardingBinding.getRoot());
        activityBoardingBinding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardingActivity.this, LogIn.class);
                startActivity(intent);
            }
        });
        activityBoardingBinding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardingActivity.this, RegisterEmail.class);
                startActivity(intent);
            }
        });
    }
}
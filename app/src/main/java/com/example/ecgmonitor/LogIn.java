package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ecgmonitor.databinding.ActivityLogInBinding;

public class LogIn extends AppCompatActivity {

    ActivityLogInBinding activityLogInBinding;
    String mCallType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInBinding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(activityLogInBinding.getRoot());
        mCallType = getIntent().getStringExtra("mCallType");

        activityLogInBinding.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, LogInEmail.class);
                intent.putExtra("mCallType",mCallType);
                startActivity(intent);
            }
        });
        activityLogInBinding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, RegisterEmail.class);
                intent.putExtra("mCallType","RESET PASSWORD");
                startActivity(intent);
            }
        });
        activityLogInBinding.clickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, RegisterEmail.class);
                intent.putExtra("mCallType",mCallType);
                startActivity(intent);
            }
        });
    }


}
package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ecgmonitor.databinding.ActivityLogInPasswordBinding;

public class LogInPassword extends AppCompatActivity {

    ActivityLogInPasswordBinding activityLogInPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInPasswordBinding = ActivityLogInPasswordBinding.inflate(getLayoutInflater());
        setContentView(activityLogInPasswordBinding.getRoot());

        activityLogInPasswordBinding.passwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInPassword.this, LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ecgmonitor.databinding.ActivityLogInEmailBinding;

public class LogInEmail extends AppCompatActivity {

    ActivityLogInEmailBinding activityLogInEmailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInEmailBinding = ActivityLogInEmailBinding.inflate(getLayoutInflater());
        setContentView(activityLogInEmailBinding.getRoot());

        activityLogInEmailBinding.emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInEmail.this, LogInPassword.class);
                startActivity(intent);
            }
        });
    }
}
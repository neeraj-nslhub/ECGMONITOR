package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ecgmonitor.databinding.ActivityRegisterPasswordBinding;

public class RegisterPassword extends AppCompatActivity {

    ActivityRegisterPasswordBinding activityRegisterPasswordBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterPasswordBinding = ActivityRegisterPasswordBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterPasswordBinding.getRoot());

        activityRegisterPasswordBinding.passwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPassword.this, RegisterConfirmPassword.class);
                startActivity(intent);
            }
        });
    }
}
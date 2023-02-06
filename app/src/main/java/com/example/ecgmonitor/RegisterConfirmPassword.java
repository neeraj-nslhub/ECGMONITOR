package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ecgmonitor.databinding.ActivityRegisterConfirmPasswordBinding;

public class RegisterConfirmPassword extends AppCompatActivity {

    ActivityRegisterConfirmPasswordBinding activityRegisterConfirmPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterConfirmPasswordBinding = ActivityRegisterConfirmPasswordBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterConfirmPasswordBinding.getRoot());

        activityRegisterConfirmPasswordBinding.confirmPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterConfirmPassword.this, LogInEmail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}
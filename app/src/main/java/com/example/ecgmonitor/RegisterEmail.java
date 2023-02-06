package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ecgmonitor.databinding.ActivityRegisterEmailBinding;

public class RegisterEmail extends AppCompatActivity {

    ActivityRegisterEmailBinding activityRegisterEmailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterEmailBinding = ActivityRegisterEmailBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterEmailBinding.getRoot());

        activityRegisterEmailBinding.emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterEmail.this, RegisterPassword.class);
                startActivity(intent);
            }
        });
    }
}
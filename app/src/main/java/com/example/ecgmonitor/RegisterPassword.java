package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ecgmonitor.databinding.ActivityRegisterPasswordBinding;

public class RegisterPassword extends AppCompatActivity {

    ActivityRegisterPasswordBinding activityRegisterPasswordBinding;
    boolean mImgBtnFlag;
    String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterPasswordBinding = ActivityRegisterPasswordBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterPasswordBinding.getRoot());
        mImgBtnFlag = false;
        mEmail = getIntent().getStringExtra("mEmail");
        activityRegisterPasswordBinding.passwordEditText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        activityRegisterPasswordBinding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(activityRegisterPasswordBinding.passwordEditText.getText().length()>5) {
                    mImgBtnFlag = true;
                    activityRegisterPasswordBinding.passwordValidateBtn.setImageTintList(getResources().getColorStateList(R.color.red));
                }
                else {
                    mImgBtnFlag = false;
                    activityRegisterPasswordBinding.passwordValidateBtn.setImageTintList(getResources().getColorStateList(R.color.grey));
                }


            }
        });

        activityRegisterPasswordBinding.passwordValidateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mImgBtnFlag)
                {
                    Intent passwordIntent = new Intent(RegisterPassword.this, RegisterConfirmPassword.class);
                    passwordIntent.putExtra("mEmail",mEmail);
                    passwordIntent.putExtra("mPassword",activityRegisterPasswordBinding.passwordEditText.getText().toString());
                    startActivity(passwordIntent);
                }
                else
                {
                    Toast.makeText(RegisterPassword.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
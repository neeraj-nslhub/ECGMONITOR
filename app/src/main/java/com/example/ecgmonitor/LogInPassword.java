package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecgmonitor.databinding.ActivityLogInPasswordBinding;

public class LogInPassword extends AppCompatActivity {

    ActivityLogInPasswordBinding activityLogInPasswordBinding;
    String mEmail;
    boolean mImgBtnFlag;
    String mCallType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInPasswordBinding = ActivityLogInPasswordBinding.inflate(getLayoutInflater());
        setContentView(activityLogInPasswordBinding.getRoot());
        mCallType = getIntent().getStringExtra("mCallType");
        mImgBtnFlag = false;
        mEmail = getIntent().getStringExtra("mEmail");
        activityLogInPasswordBinding.passwordEditText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        activityLogInPasswordBinding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(activityLogInPasswordBinding.passwordEditText.getText().length()>5) {
                    mImgBtnFlag = true;
                    activityLogInPasswordBinding.passwordValidateBtn.setImageTintList(getResources().getColorStateList(R.color.red));
                }
                else {
                    mImgBtnFlag = false;
                    activityLogInPasswordBinding.passwordValidateBtn.setImageTintList(getResources().getColorStateList(R.color.grey));
                }
            }
        });

        activityLogInPasswordBinding.passwordValidateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mImgBtnFlag)
                {
                    Intent passwordIntent = new Intent(LogInPassword.this, ProgressAnim.class);
                    passwordIntent.putExtra("mCallType", mCallType);
                    passwordIntent.putExtra("mEmail",mEmail);
                    passwordIntent.putExtra("mPassword",activityLogInPasswordBinding.passwordEditText.getText().toString());
                    startActivity(passwordIntent);
                }
                else
                {
                    Toast.makeText(LogInPassword.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
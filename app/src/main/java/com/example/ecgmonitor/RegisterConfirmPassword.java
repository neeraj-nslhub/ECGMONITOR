package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecgmonitor.databinding.ActivityRegisterConfirmPasswordBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterConfirmPassword extends AppCompatActivity {

    ActivityRegisterConfirmPasswordBinding activityRegisterConfirmPasswordBinding;
    String mEmail, mPassword;
    boolean mImgBtnFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterConfirmPasswordBinding = ActivityRegisterConfirmPasswordBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterConfirmPasswordBinding.getRoot());
        activityRegisterConfirmPasswordBinding.confirmPasswordEditText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        mEmail = getIntent().getStringExtra("mEmail");
        mPassword = getIntent().getStringExtra("mPassword");
        mImgBtnFlag = false;

        activityRegisterConfirmPasswordBinding.confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(isEditTextContainConfirmPassword(activityRegisterConfirmPasswordBinding.confirmPasswordEditText, mPassword)) {
                    mImgBtnFlag = true;
                    activityRegisterConfirmPasswordBinding.confirmPasswordValidateBtn.setImageTintList(getResources().getColorStateList(R.color.red));
                }
                else {
                    mImgBtnFlag = false;
                    activityRegisterConfirmPasswordBinding.confirmPasswordValidateBtn.setImageTintList(getResources().getColorStateList(R.color.grey));
                }

            }
        });

        activityRegisterConfirmPasswordBinding.confirmPasswordValidateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mImgBtnFlag)
                {

                    Intent intent = new Intent(RegisterConfirmPassword.this, ProgressAnim.class);
                    intent.putExtra("mCallType", "REGISTRATION");
                    intent.putExtra("mEmail",mEmail);
                    intent.putExtra("mPassword",mPassword);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(RegisterConfirmPassword.this, "Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public static boolean isEditTextContainConfirmPassword(EditText argEditText, String password) {
        if(argEditText.getText().toString().equals(password))
            return true;
        else
            return false;
    }

}
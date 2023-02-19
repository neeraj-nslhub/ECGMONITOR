package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecgmonitor.databinding.ActivityLogInEmailBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInEmail extends AppCompatActivity {

    ActivityLogInEmailBinding activityLogInEmailBinding;
    boolean mImgBtnFlag;
    String mCallType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInEmailBinding = ActivityLogInEmailBinding.inflate(getLayoutInflater());
        setContentView(activityLogInEmailBinding.getRoot());
        mCallType = getIntent().getStringExtra("mCallType");
        mImgBtnFlag = false;
        activityLogInEmailBinding.emailEditText.requestFocus();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        activityLogInEmailBinding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (isEditTextContainEmail(activityLogInEmailBinding.emailEditText)) {
                    mImgBtnFlag = true;
                    activityLogInEmailBinding.emailValidateBtn.setImageTintList(getResources().getColorStateList(R.color.red));
                } else {
                    mImgBtnFlag = false;
                    activityLogInEmailBinding.emailValidateBtn.setImageTintList(getResources().getColorStateList(R.color.grey));
                }
            }

        });

        activityLogInEmailBinding.emailValidateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mImgBtnFlag)
                {
                    Intent emailIntent = new Intent(LogInEmail.this,LogInPassword.class);
                    emailIntent.putExtra("mCallType", mCallType);
                    emailIntent.putExtra("mEmail", activityLogInEmailBinding.emailEditText.getText().toString());
                    startActivity(emailIntent);
                }
                else
                {
                    Toast.makeText(LogInEmail.this, "Incorrect Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public static boolean isEditTextContainEmail(EditText argEditText) {
        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(argEditText.getText());
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import com.example.ecgmonitor.databinding.ActivityRegisterEmailBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterEmail extends AppCompatActivity {

    ActivityRegisterEmailBinding activityRegisterEmailBinding;
    boolean mImgBtnFlag;
    String mCallType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterEmailBinding = ActivityRegisterEmailBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterEmailBinding.getRoot());
        mCallType = getIntent().getStringExtra("mCallType");
        mImgBtnFlag = false;
        if(mCallType.equals("RESET PASSWORD"))
            activityRegisterEmailBinding.headerText.setText("RESET PASSWORD");
        activityRegisterEmailBinding.emailEditText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        activityRegisterEmailBinding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (isEditTextContainEmail(activityRegisterEmailBinding.emailEditText)) {
                    mImgBtnFlag = true;
                    activityRegisterEmailBinding.emailValidateBtn.setImageTintList(getResources().getColorStateList(R.color.red));
                } else {
                    mImgBtnFlag = false;
                    activityRegisterEmailBinding.emailValidateBtn.setImageTintList(getResources().getColorStateList(R.color.grey));
                }
            }

        });

        activityRegisterEmailBinding.emailValidateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mImgBtnFlag)
                {
                    Intent emailIntent = new Intent(RegisterEmail.this,RegisterPassword.class);
                    emailIntent.putExtra("mCallType", mCallType);
                    emailIntent.putExtra("mEmail", activityRegisterEmailBinding.emailEditText.getText().toString());
                    startActivity(emailIntent);
                }
                else
                {
                    Toast.makeText(RegisterEmail.this, "Incorrect Email", Toast.LENGTH_SHORT).show();
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
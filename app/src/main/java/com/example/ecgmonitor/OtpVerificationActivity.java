package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.ecgmonitor.apiclients.RetrofitApi;
import com.example.ecgmonitor.databinding.ActivityOtpVerificationBinding;
import com.example.ecgmonitor.interfaces.ServerInterfaces;
import com.example.ecgmonitor.model.GenerateOtp.GenerateOtpBody;
import com.example.ecgmonitor.model.GenerateOtp.GenerateOtpResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationActivity extends AppCompatActivity {

    ActivityOtpVerificationBinding activityOtpVerificationBinding;
    String mEmail, mCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOtpVerificationBinding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(activityOtpVerificationBinding.getRoot());
        mEmail = getIntent().getStringExtra("mEmail");
        activityOtpVerificationBinding.mtextTwo.setText(mEmail);

        activityOtpVerificationBinding.resendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callResendApi(mEmail);
            }
        });

        activityOtpVerificationBinding.editOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (activityOtpVerificationBinding.editOne.getText().toString().length() == 1) //size as per your requirement
                {
                    activityOtpVerificationBinding.editOne.clearFocus();
                    activityOtpVerificationBinding.editTwo.requestFocus();
                    activityOtpVerificationBinding.editTwo.setCursorVisible(true);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (activityOtpVerificationBinding.editOne.getText().toString().length() == 0) //size as per your requirement
                {
                    activityOtpVerificationBinding.editOne.requestFocus();

                }

                if(activityOtpVerificationBinding.editOne.getText().toString().length() == 1 && activityOtpVerificationBinding.editTwo.getText().toString().length() == 1 && activityOtpVerificationBinding.editThree.getText().toString().length() == 1 && activityOtpVerificationBinding.editFour.getText().toString().length() == 1 && activityOtpVerificationBinding.editFive.getText().toString().length() == 1 && activityOtpVerificationBinding.editSix.getText().toString().length() == 1)
                {
                    showProgress();
                }
            }
        });
        activityOtpVerificationBinding.editTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (activityOtpVerificationBinding.editTwo.getText().toString().length() == 1) //size as per your requirement
                {
                    activityOtpVerificationBinding.editTwo.clearFocus();
                    activityOtpVerificationBinding.editThree.requestFocus();
                    activityOtpVerificationBinding.editThree.setCursorVisible(true);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (activityOtpVerificationBinding.editTwo.getText().toString().length() == 0) //size as per your requirement
                {
                    activityOtpVerificationBinding.editTwo.requestFocus();

                }

                if(activityOtpVerificationBinding.editOne.getText().toString().length() == 1 && activityOtpVerificationBinding.editTwo.getText().toString().length() == 1 && activityOtpVerificationBinding.editThree.getText().toString().length() == 1 && activityOtpVerificationBinding.editFour.getText().toString().length() == 1 && activityOtpVerificationBinding.editFive.getText().toString().length() == 1 && activityOtpVerificationBinding.editSix.getText().toString().length() == 1)
                {
                    showProgress();
                }
            }
        });

        activityOtpVerificationBinding.editThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (activityOtpVerificationBinding.editThree.getText().toString().length() == 1) //size as per your requirement
                {
                    activityOtpVerificationBinding.editThree.clearFocus();
                    activityOtpVerificationBinding.editFour.requestFocus();
                    activityOtpVerificationBinding.editFour.setCursorVisible(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (activityOtpVerificationBinding.editThree.getText().toString().length() == 0) //size as per your requirement
                {
                    activityOtpVerificationBinding.editThree.requestFocus();

                }

                if(activityOtpVerificationBinding.editOne.getText().toString().length() == 1 && activityOtpVerificationBinding.editTwo.getText().toString().length() == 1 && activityOtpVerificationBinding.editThree.getText().toString().length() == 1 && activityOtpVerificationBinding.editFour.getText().toString().length() == 1 && activityOtpVerificationBinding.editFive.getText().toString().length() == 1 && activityOtpVerificationBinding.editSix.getText().toString().length() == 1)
                {
                    showProgress();
                }
            }
        });
        activityOtpVerificationBinding.editFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (activityOtpVerificationBinding.editFour.getText().toString().length() == 1) //size as per your requirement
                {
                    activityOtpVerificationBinding.editFour.clearFocus();
                    activityOtpVerificationBinding.editFive.requestFocus();
                    activityOtpVerificationBinding.editFive.setCursorVisible(true);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (activityOtpVerificationBinding.editFour.getText().toString().length() == 0) //size as per your requirement
                {
                    activityOtpVerificationBinding.editFour.requestFocus();

                }

                if(activityOtpVerificationBinding.editOne.getText().toString().length() == 1 && activityOtpVerificationBinding.editTwo.getText().toString().length() == 1 && activityOtpVerificationBinding.editThree.getText().toString().length() == 1 && activityOtpVerificationBinding.editFour.getText().toString().length() == 1 && activityOtpVerificationBinding.editFive.getText().toString().length() == 1 && activityOtpVerificationBinding.editSix.getText().toString().length() == 1)
                {
                      showProgress();
                }
            }
        });


        activityOtpVerificationBinding.editFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (activityOtpVerificationBinding.editFive.getText().toString().length() == 1) //size as per your requirement
                {
                    activityOtpVerificationBinding.editFive.clearFocus();
                    activityOtpVerificationBinding.editSix.requestFocus();
                    activityOtpVerificationBinding.editSix.setCursorVisible(true);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (activityOtpVerificationBinding.editFive.getText().toString().length() == 0) //size as per your requirement
                {
                    activityOtpVerificationBinding.editFive.requestFocus();

                }

                if(activityOtpVerificationBinding.editOne.getText().toString().length() == 1 && activityOtpVerificationBinding.editTwo.getText().toString().length() == 1 && activityOtpVerificationBinding.editThree.getText().toString().length() == 1 && activityOtpVerificationBinding.editFour.getText().toString().length() == 1 && activityOtpVerificationBinding.editFive.getText().toString().length() == 1 && activityOtpVerificationBinding.editSix.getText().toString().length() == 1)
                {
                      showProgress();
                }
            }
        });
        activityOtpVerificationBinding.editSix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (activityOtpVerificationBinding.editSix.getText().toString().length() == 1) //size as per your requirement
                {
                    activityOtpVerificationBinding.editSix.clearFocus();
                    activityOtpVerificationBinding.editSix.requestFocus();
                    activityOtpVerificationBinding.editSix.setCursorVisible(true);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (activityOtpVerificationBinding.editSix.getText().toString().length() == 0) //size as per your requirement
                {
                    activityOtpVerificationBinding.editSix.requestFocus();

                }
                if(activityOtpVerificationBinding.editOne.getText().toString().length() == 1 && activityOtpVerificationBinding.editTwo.getText().toString().length() == 1 && activityOtpVerificationBinding.editThree.getText().toString().length() == 1 && activityOtpVerificationBinding.editFour.getText().toString().length() == 1 && activityOtpVerificationBinding.editFive.getText().toString().length() == 1 && activityOtpVerificationBinding.editSix.getText().toString().length() == 1)
                {
                      showProgress();
                }
            }
        });
    }

    private void callResendApi(String email) {
        final GenerateOtpBody generateOtpBody = new GenerateOtpBody(email);
        ServerInterfaces serverApiInterface = RetrofitApi.getApiClient().create(ServerInterfaces.class);
        Call<GenerateOtpResponseBody> call = serverApiInterface.generatingOtp(generateOtpBody);
        call.enqueue(new Callback<GenerateOtpResponseBody>() {
            @Override
            public void onResponse(Call<GenerateOtpResponseBody> call, Response<GenerateOtpResponseBody> response) {
                GenerateOtpResponseBody generateOtpResponseBody = response.body();

                    Toast.makeText(OtpVerificationActivity.this,""+generateOtpResponseBody.getData().getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GenerateOtpResponseBody> call, Throwable t) {
                Toast.makeText(OtpVerificationActivity.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress() {
        mCode = activityOtpVerificationBinding.editOne.getText().toString()+ activityOtpVerificationBinding.editTwo.getText().toString()+activityOtpVerificationBinding.editThree.getText().toString()+activityOtpVerificationBinding.editFour.getText().toString()+activityOtpVerificationBinding.editFive.getText().toString()+activityOtpVerificationBinding.editSix.getText().toString();
        Intent intent = new Intent(OtpVerificationActivity.this, ProgressAnim.class);
        intent.putExtra("mCallType","REGISTRATION OTP");
        intent.putExtra("mEmail",mEmail);
        intent.putExtra("mCode",mCode);
        startActivity(intent);

    }
}
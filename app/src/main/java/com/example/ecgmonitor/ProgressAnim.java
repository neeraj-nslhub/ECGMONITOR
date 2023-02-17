package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.example.ecgmonitor.apiclients.RetrofitApi;
import com.example.ecgmonitor.databinding.ActivityProgressAnimBinding;
import com.example.ecgmonitor.interfaces.ServerInterfaces;
import com.example.ecgmonitor.model.SignInBody;
import com.example.ecgmonitor.model.SignInResponseBody;
import com.example.ecgmonitor.model.SignUp.SignUpBody;
import com.example.ecgmonitor.model.SignUp.SignUpResponseBody;
import com.example.ecgmonitor.model.VerifyOtpBody;
import com.example.ecgmonitor.model.VerifyOtpResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressAnim extends AppCompatActivity {

    String mCallType, mEmail, mPassword, mCode;
    Animation fadeInOutAnimation;
    ActivityProgressAnimBinding activityProgressAnimBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProgressAnimBinding = ActivityProgressAnimBinding.inflate(getLayoutInflater());
        setContentView(activityProgressAnimBinding.getRoot());
        mCallType = getIntent().getStringExtra("mCallType");
        if(mCallType.equals("LOGIN") || mCallType.equals("REGISTRATION"))
        {
            mEmail = getIntent().getStringExtra("mEmail");
            mPassword = getIntent().getStringExtra("mPassword");
        } else if (mCallType.equals("REGISTRATION OTP")) {
            mEmail = getIntent().getStringExtra("mEmail");
            mCode = getIntent().getStringExtra("mCode");
        }
        fadeInOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_out_animation);
        activityProgressAnimBinding.image.setAnimation(fadeInOutAnimation);

        if(mCallType.equals("LOGIN"))
            callLogInApi(mEmail, mPassword);
        else if(mCallType.equals("REGISTRATION"))
            callRegistrationApi(mEmail, mPassword);
        else if (mCallType.equals("REGISTRATION OTP"))
            callRegistrationOtpVerificationApi(mEmail, mCode);
    }

    private void callRegistrationOtpVerificationApi(String email, String otp) {
        final VerifyOtpBody verifyOtpBody = new VerifyOtpBody(email, otp);
        ServerInterfaces serverApiInterface = RetrofitApi.getApiClient().create(ServerInterfaces.class);
        Call<VerifyOtpResponseBody> call = serverApiInterface.verifyingRegisterOtp(verifyOtpBody);
        call.enqueue(new Callback<VerifyOtpResponseBody>() {
            @Override
            public void onResponse(Call<VerifyOtpResponseBody> call, Response<VerifyOtpResponseBody> response) {
                VerifyOtpResponseBody verifyOtpResponseBody = response.body();
                if(verifyOtpResponseBody.isStatus())
                {
                    Toast.makeText(ProgressAnim.this,verifyOtpResponseBody.getMessage(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProgressAnim.this, LogInEmail.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ProgressAnim.this,""+verifyOtpResponseBody.getMessage(),Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<VerifyOtpResponseBody> call, Throwable t) {
                Toast.makeText(ProgressAnim.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void callRegistrationApi(String email, String password)
    {
        final SignUpBody signUpBody = new SignUpBody(email, password);
        ServerInterfaces serverApiInterface = RetrofitApi.getApiClient().create(ServerInterfaces.class);
        Call<SignUpResponseBody> call = serverApiInterface.registering(signUpBody);
        call.enqueue(new Callback<SignUpResponseBody>() {
            @Override
            public void onResponse(Call<SignUpResponseBody> call, Response<SignUpResponseBody> response) {
                SignUpResponseBody signUpResponseBody = response.body();
                if(signUpResponseBody.getData().isStatus())
                {
                    Toast.makeText(ProgressAnim.this,""+signUpResponseBody.getData().getMessage(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProgressAnim.this, OtpVerificationActivity.class);
                    intent.putExtra("mEmail",mEmail);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(ProgressAnim.this,""+signUpResponseBody.getData().getMessage(),Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<SignUpResponseBody> call, Throwable t) {
                Toast.makeText(ProgressAnim.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void callLogInApi(String email, String password)
    {
        final SignInBody signInBody = new SignInBody(email, password);
        ServerInterfaces serverApiInterface = RetrofitApi.getApiClient().create(ServerInterfaces.class);
        Call<SignInResponseBody> call = serverApiInterface.signning(signInBody);
        call.enqueue(new Callback<SignInResponseBody>() {
            @Override
            public void onResponse(Call<SignInResponseBody> call, Response<SignInResponseBody> response) {
                SignInResponseBody signInResponseBody = response.body();
                if(signInResponseBody.isStatus())
                {
                    Toast.makeText(ProgressAnim.this,signInResponseBody.getMessage(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProgressAnim.this, LandingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ProgressAnim.this,"Incorrect UserName or Password",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<SignInResponseBody> call, Throwable t) {
                Toast.makeText(ProgressAnim.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
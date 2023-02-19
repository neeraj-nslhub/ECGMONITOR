package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.example.ecgmonitor.apiclients.RetrofitApi;
import com.example.ecgmonitor.databinding.ActivityProgressAnimBinding;
import com.example.ecgmonitor.interfaces.ServerInterfaces;
import com.example.ecgmonitor.model.GenerateOtp.GenerateOtpBody;
import com.example.ecgmonitor.model.GenerateOtp.GenerateOtpResponseBody;
import com.example.ecgmonitor.model.ResetPassword.ResetPasswordOtpBody;
import com.example.ecgmonitor.model.ResetPassword.ResetPasswordOtpResponseBody;
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
        Log.d("mCallType","Anim"+mCallType);
        if((mCallType.equals("LOGIN")) || (mCallType.equals("REGISTER")) || (mCallType.equals("RESET PASSWORD")))
        {
            mEmail = getIntent().getStringExtra("mEmail");
            mPassword = getIntent().getStringExtra("mPassword");
        } else if (mCallType.equals("REGISTRATION OTP")) {
            mEmail = getIntent().getStringExtra("mEmail");
            mCode = getIntent().getStringExtra("mCode");
        }
        else if(mCallType.equals("RESET PASSWORD OTP"))
        {
            mEmail = getIntent().getStringExtra("mEmail");
            mPassword = getIntent().getStringExtra("mPassword");
            mCode = getIntent().getStringExtra("mCode");
        }
        fadeInOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_out_animation);
        activityProgressAnimBinding.image.setAnimation(fadeInOutAnimation);
        Log.d("email", mEmail);
        Log.d("email", mPassword);
        if(mCallType.equals("LOGIN")) {
            callLogInApi(mEmail, mPassword);
        }
        else if (mCallType.equals("RESET PASSWORD OTP")) {
            callForgotPasswordOtpVerifyApi(mEmail, mPassword, mCode);
        }
        else if (mCallType.equals("RESET PASSWORD")) {
            callForgotPasswordOtpApi(mEmail);
        }
//        else if(mCallType.equals("REGISTER"))
//            callRegistrationApi(mEmail, mPassword);
//        else if (mCallType.equals("REGISTER OTP"))
//            callRegistrationOtpVerificationApi(mEmail, mCode);

    }

    private void callForgotPasswordOtpVerifyApi(String email, String password, String otp) {
        Log.d("mCallType","callForgotPasswordOtpVerifyApi"+mCallType);
        final ResetPasswordOtpBody resetPasswordOtpBody = new ResetPasswordOtpBody(email, password, otp);
        ServerInterfaces serverApiInterface = RetrofitApi.getApiClient().create(ServerInterfaces.class);
        Call<ResetPasswordOtpResponseBody> call = serverApiInterface.resetingPassword(resetPasswordOtpBody);
        call.enqueue(new Callback<ResetPasswordOtpResponseBody>() {
            @Override
            public void onResponse(Call<ResetPasswordOtpResponseBody> call, Response<ResetPasswordOtpResponseBody> response) {
                ResetPasswordOtpResponseBody resetPasswordOtpResponseBody = response.body();
                if(resetPasswordOtpResponseBody.getData().isStatus())
                {
                    Toast.makeText(ProgressAnim.this,resetPasswordOtpResponseBody.getData().getMessage(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProgressAnim.this, LogInEmail.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ProgressAnim.this,""+resetPasswordOtpResponseBody.getData().getMessage(),Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<ResetPasswordOtpResponseBody> call, Throwable t) {
                Toast.makeText(ProgressAnim.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    private void callRegistrationOtpVerificationApi(String email, String otp) {
        Log.d("mCallType","callRegistrationOtpVerificationApi"+mCallType);
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
                Log.d("MYqwer", "OnSuccess");
                SignUpResponseBody signUpResponseBody = response.body();
                Log.d("MYqwer", "OnSuccess12"+response);
//                Log.d("register","yes");
//                if(signUpResponseBody.getData().isStatus())
//                {
//                    Log.d("register","yes"+signUpResponseBody);
//                  //  Toast.makeText(ProgressAnim.this,""+signUpResponseBody.getData().getMessage(),Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(ProgressAnim.this, OtpVerificationActivity.class);
//                    intent.putExtra("mCallType", mCallType);
//                    intent.putExtra("mEmail",mEmail);
//                    startActivity(intent);
//                    finish();
//                }
//                else
//                {
//                    Log.d("register","no"+signUpResponseBody.getData().getMessage());
//                    Toast.makeText(ProgressAnim.this,""+signUpResponseBody.getData().getMessage(),Toast.LENGTH_SHORT).show();
//                    //finish();
//                }

            }

            @Override
            public void onFailure(Call<SignUpResponseBody> call, Throwable t) {
                Log.d("MYqwer",t.getMessage());

              //  Toast.makeText(ProgressAnim.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
              //  finish();
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
    private void callForgotPasswordOtpApi(String email)
    {
        Log.d("mCallType","callForgotPasswordOtpApi"+mCallType);
        final GenerateOtpBody generateOtpBody = new GenerateOtpBody(email);
        ServerInterfaces serverApiInterface = RetrofitApi.getApiClient().create(ServerInterfaces.class);
        Call<GenerateOtpResponseBody> call = serverApiInterface.generatingOtp(generateOtpBody);
        call.enqueue(new Callback<GenerateOtpResponseBody>() {
            @Override
            public void onResponse(Call<GenerateOtpResponseBody> call, Response<GenerateOtpResponseBody> response) {
                GenerateOtpResponseBody generateOtpResponseBody = response.body();
                if(generateOtpResponseBody.getData().isStatus())
                {
                    Toast.makeText(ProgressAnim.this,""+generateOtpResponseBody.getData().getMessage(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProgressAnim.this, OtpVerificationActivity.class);
                    intent.putExtra("mCallType", mCallType);
                    intent.putExtra("mEmail",mEmail);
                    intent.putExtra("mPassword", mPassword);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ProgressAnim.this,""+generateOtpResponseBody.getData().getMessage(),Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<GenerateOtpResponseBody> call, Throwable t) {
                Toast.makeText(ProgressAnim.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
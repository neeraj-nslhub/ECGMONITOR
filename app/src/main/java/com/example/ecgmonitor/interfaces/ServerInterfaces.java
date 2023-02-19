package com.example.ecgmonitor.interfaces;

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
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServerInterfaces {

    @POST("/account/login/")
    Call<SignInResponseBody> signning(@Body SignInBody signInBody);

    @POST("/account/register/")
    Call<SignUpResponseBody> registering(@Body SignUpBody signUpBody);

    @POST("/account/verify_otp/")
    Call<VerifyOtpResponseBody> verifyingRegisterOtp(@Body VerifyOtpBody verifyOtpBody);

    @POST("/account/forgot_password_otp/")
    Call<GenerateOtpResponseBody> generatingOtp(@Body GenerateOtpBody generateOtpBody);

    @POST("/account/forgot_password_verify_otp/")
    Call<ResetPasswordOtpResponseBody> resetingPassword(@Body ResetPasswordOtpBody resetPasswordOtpBody);

}

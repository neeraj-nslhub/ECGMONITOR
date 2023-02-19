package com.example.ecgmonitor.model.ResetPassword;

public class ResetPasswordOtpBody {

    String email;
    String password;
    String otp;

    public ResetPasswordOtpBody(String email, String password, String otp) {
        this.email = email;
        this.password = password;
        this.otp = otp;
    }
}

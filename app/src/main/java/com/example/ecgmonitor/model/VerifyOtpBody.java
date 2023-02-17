package com.example.ecgmonitor.model;

public class VerifyOtpBody {
    String email;
    String otp;

    public VerifyOtpBody(String email, String otp) {

        this.email = email;
        this.otp = otp;
    }
}

package com.example.ecgmonitor.model.ResetPassword;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordOtpResponseBody{

	@SerializedName("data")
	private Data data;

	public Data getData(){
		return data;
	}
}
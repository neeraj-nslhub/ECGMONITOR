package com.example.ecgmonitor.model.GenerateOtp;

import com.google.gson.annotations.SerializedName;

public class GenerateOtpResponseBody{

	@SerializedName("data")
	private Data data;

	public Data getData(){
		return data;
	}
}
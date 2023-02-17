package com.example.ecgmonitor.model.SignUp;

import com.google.gson.annotations.SerializedName;

public class SignUpResponseBody{

	@SerializedName("data")
	private Data data;

	public Data getData(){
		return data;
	}
}
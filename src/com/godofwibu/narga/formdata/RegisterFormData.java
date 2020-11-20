package com.godofwibu.narga.formdata;

import com.godofwibu.narga.utils.ParameterName;

import lombok.Data;

@Data
public class RegisterFormData {
	private String userId;
	
	private String name;
	
	private String password;
	
	@ParameterName("repeatPassword")
	private String rePassword;
	
	private String personalId;
}

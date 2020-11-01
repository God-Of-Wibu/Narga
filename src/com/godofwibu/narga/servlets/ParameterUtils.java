package com.godofwibu.narga.servlets;

import java.util.Map;

import com.godofwibu.narga.entities.Gender;

public class ParameterUtils {
	public static String getParamAsString(Map<String, String[]> params, String name) {
		return params.get(name)[0];
	}
	
	public static int getParamAsInteger(Map<String, String[]> params, String name) {
		return Integer.parseInt(getParamAsString(params, name));
	}
	
	public static Gender getParamAsGender(Map<String, String[]> params, String name) {
		return Gender.MALE.name().equals(getParamAsString(params, name)) ? Gender.MALE : Gender.FEMALE;
	}
}

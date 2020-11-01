package com.godofwibu.narga.utils;

public class StringConverter implements IConverter<String>{

	@Override
	public String convert(String[] input) {
		return input[0];
	}

	
}

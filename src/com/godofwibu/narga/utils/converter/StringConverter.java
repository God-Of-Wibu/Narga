package com.godofwibu.narga.utils.converter;

import com.godofwibu.narga.utils.IConverter;

public class StringConverter implements IConverter<String>{

	@Override
	public String convert(String[] input) {
		return input[0];
	}

	
}

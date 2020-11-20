package com.godofwibu.narga.utils.converter;

import com.godofwibu.narga.utils.IConverter;

public class StringConverter implements IConverter<String>{

	@Override
	public String convert(String[] input) {
		if ("null".equals(input[0]))
			return null;
		return input[0];
	}

	
}

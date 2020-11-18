package com.godofwibu.narga.utils.converter;

import com.godofwibu.narga.utils.IConverter;

public class IntegerConverter implements IConverter<Integer> {

	@Override
	public Integer convert(String[] input) {
		if ("null".equals(input[0]))
			return null;
		return Integer.parseInt(input[0]);
	}

}

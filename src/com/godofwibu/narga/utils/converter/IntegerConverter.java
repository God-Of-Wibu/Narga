package com.godofwibu.narga.utils.converter;

import com.godofwibu.narga.utils.IConverter;

public class IntegerConverter implements IConverter<Integer>{

	@Override
	public Integer convert(String[] input) {
		return Integer.parseInt(input[0]);
	}

}

package com.godofwibu.narga.utils;

public class IntegerConverter implements IConverter<Integer>{

	@Override
	public Integer convert(String[] input) {
		return Integer.parseInt(input[0]);
	}

}

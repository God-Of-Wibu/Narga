package com.godofwibu.narga.utils.converter;

import com.godofwibu.narga.utils.IConverter;

public class IntegerArrayConverter implements IConverter<Integer[]> {

	@Override
	public Integer[] convert(String[] input) {
		Integer[] output = new Integer[input.length];
		int i = 0;
		for (String v : input) {
			output[i++] = Integer.parseInt(v);
		}
 		return output;
	}

}

package com.godofwibu.narga.utils.converter;

import java.util.Arrays;

import com.godofwibu.narga.utils.DateTime;
import com.godofwibu.narga.utils.IConverter;
import com.google.gson.Gson;

public class DateTimeArrayConverter implements IConverter<DateTime[]> {

	private Gson gson;
	
	@Override
	public DateTime[] convert(String[] input) {
		
		System.out.println(Arrays.toString(input));
		
		DateTime[] dateTimes = new DateTime[input.length];
		
		
		
		return null;
	}

}

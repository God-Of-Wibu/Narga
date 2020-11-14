package com.godofwibu.narga.utils.converter;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;

import com.godofwibu.narga.utils.DateTime;
import com.godofwibu.narga.utils.IConverter;
import com.google.gson.Gson;

public class DateTimeArrayConverter implements IConverter<DateTime[]> {

	private static class Raw {
		String date;
		String time;
	}
	
	private Gson gson;
	
	public DateTimeArrayConverter() {
		gson = new Gson();
	}
	
	@Override
	public DateTime[] convert(String[] input) {
		
		DateTime[] dateTimes = new DateTime[input.length];
		
		for (int i = 0; i < input.length; ++i) {
			Raw raw = gson.fromJson(input[i], Raw.class);
			
			dateTimes[i] = new DateTime(Date.valueOf(raw.date), Time.valueOf(raw.time));
		}
		
		
		return dateTimes;
	}

}

package com.godofwibu.narga.utils;

import com.godofwibu.narga.entities.Gender;

public class GenderConverter implements IConverter<Gender> {
	@Override
	public Gender convert(String[] input) {
		return input[0].equals(Gender.FEMALE.name()) ? Gender.FEMALE : Gender.MALE;
	}

}

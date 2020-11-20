package com.godofwibu.narga.utils.converter;

import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.utils.IConverter;

public class GenderConverter implements IConverter<Gender> {
	@Override
	public Gender convert(String[] input) {
		return input[0].equals(Gender.FEMALE.name()) ? Gender.FEMALE : Gender.MALE;
	}

}

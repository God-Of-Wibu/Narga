package com.godofwibu.narga.dto;

import javax.servlet.http.Part;

import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.utils.ParameterName;
import com.godofwibu.narga.utils.Required;

import lombok.Data;

@Data
public class AddActorFormData {
	@Required
	String name;
	
	@ParameterName("age")
	Integer age;
	
	@Required
	@ParameterName("gender")
	Gender gender;
	
	@ParameterName("avatar")
	Part avatarPart;
	
	@ParameterName("country")
	String countryId;
}

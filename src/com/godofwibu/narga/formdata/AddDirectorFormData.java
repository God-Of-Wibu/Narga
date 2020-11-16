package com.godofwibu.narga.formdata;

import javax.servlet.http.Part;

import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.utils.ParameterName;
import lombok.Data;
@Data
public class AddDirectorFormData {

	String name;
	
	@ParameterName("age")
	Integer age;
	
	@ParameterName("gender")
	Gender gender;
	
	@ParameterName("avatar")
	Part avatarPart;
	
	@ParameterName("country")
	String countryId;
}

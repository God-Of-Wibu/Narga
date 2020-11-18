package com.godofwibu.narga.formdata;

import javax.servlet.http.Part;

import com.godofwibu.narga.utils.OptionalParameter;
import com.godofwibu.narga.utils.ParameterName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFilmFormData {

	@ParameterName("title")
	private String title;

	@ParameterName("director")
	@OptionalParameter(defaultValue = "null")
	private Integer director;
	
	@ParameterName("categories")
	@OptionalParameter
	private Integer[] categories;

	@ParameterName("casting")
	@OptionalParameter
	private Integer[] casting;

	@ParameterName("poster")
	private Part posterPart;

	@ParameterName("description")
	private String description;

	@ParameterName("country")
	private String countryName;

	@ParameterName("runningTime")
	private Integer runningTime;
}

package com.godofwibu.narga.dto;

import javax.servlet.http.Part;

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
	private Integer director;
	
	@ParameterName("categories")
	private Integer[] categories;

	@ParameterName("casting")
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

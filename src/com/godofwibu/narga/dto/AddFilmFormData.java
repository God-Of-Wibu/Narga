package com.godofwibu.narga.dto;

import javax.servlet.http.Part;

import com.godofwibu.narga.utils.ParameterName;
import com.godofwibu.narga.utils.Required;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFilmFormData {
	
	@Required
	@ParameterName("title")
	private String title;
	
	@Required
	@ParameterName("director")
	private Integer director;
	
	@Required
	@ParameterName("categories")
	private Integer[] categories;
	
	@Required
	@ParameterName("casting")
	private Integer[] casting;
	
	@Required
	@ParameterName("poster")
	private Part posterPart;
	
	@ParameterName("description")
	private String description;
	
	@Required
	@ParameterName("country")
	private String countryName;

	@Required
	@ParameterName("runningTime")
	private Integer runningTime;
}

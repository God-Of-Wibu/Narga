package com.godofwibu.narga.dto;

import com.godofwibu.narga.utils.DateTime;
import com.godofwibu.narga.utils.ParameterName;

public class NewIssueFormData {
	
	@ParameterName("film")
	private Integer filmId;
	
	@ParameterName("times")
	private DateTime[] times;
	
	@ParameterName("basicCost")
	private Integer basicCost;
	
	@ParameterName("vipCost")
	private Integer vipCost;
}

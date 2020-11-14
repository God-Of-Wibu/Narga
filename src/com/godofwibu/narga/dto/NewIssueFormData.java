package com.godofwibu.narga.dto;

import com.godofwibu.narga.utils.DateTime;
import com.godofwibu.narga.utils.ParameterName;

import lombok.Data;

@Data
public class NewIssueFormData {
	
	@ParameterName("filmId")
	private Integer filmId;
	
	@ParameterName("basicCost")
	private Integer basicCost;
	
	@ParameterName("vipCost")
	private Integer vipCost;
	
	@ParameterName("dateTimes[]")
	private DateTime[] dateTimes;
}

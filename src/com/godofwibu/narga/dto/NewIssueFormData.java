package com.godofwibu.narga.dto;

import java.sql.Date;

import com.godofwibu.narga.utils.ParameterName;

public class NewIssueFormData {
	
	@ParameterName("film")
	private Integer filmId;
	
	@ParameterName("date")
	private Date date;
	
	@ParameterName("basicCost")
	private Integer basicCost;
	
	@ParameterName("vipCost")
	private Integer vipCost;
}

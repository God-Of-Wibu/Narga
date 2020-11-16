package com.godofwibu.narga.dto;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Issue_DTO_API_Get {
	private int id;
	private Time time;
}

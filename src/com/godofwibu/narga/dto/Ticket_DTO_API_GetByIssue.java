package com.godofwibu.narga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket_DTO_API_GetByIssue {
	private int id;
	private String position;
	private boolean availiable;
	private int cost;
}

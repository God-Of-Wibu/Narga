package com.godofwibu.narga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketJsonData {
	private int id;
	private String position;
	private boolean availiable;
	private int cost;
}

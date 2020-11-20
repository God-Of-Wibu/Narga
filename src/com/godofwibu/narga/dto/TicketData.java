package com.godofwibu.narga.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.Data;

@Data
public class TicketData {
	private String filmTitle;
	private int cost;
	private Date date;
	private Time startingTime;
	private String position;
}

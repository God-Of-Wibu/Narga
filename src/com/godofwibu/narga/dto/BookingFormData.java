package com.godofwibu.narga.dto;

import com.godofwibu.narga.utils.ParameterName;

import lombok.Data;

@Data
public class BookingFormData {
	@ParameterName("tickets")
	Integer[] ticketIds;
}

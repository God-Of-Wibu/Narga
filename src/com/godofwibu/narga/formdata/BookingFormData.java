package com.godofwibu.narga.formdata;

import com.godofwibu.narga.utils.ParameterName;

import lombok.Data;

@Data
public class BookingFormData {
	@ParameterName("tickets")
	Integer[] ticketIds;
}

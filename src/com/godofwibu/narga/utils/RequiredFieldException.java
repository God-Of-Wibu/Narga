package com.godofwibu.narga.utils;

import lombok.Getter;
import lombok.Setter;

public class RequiredFieldException extends RuntimeException {
	@Setter
	@Getter
	private String fieldName;
	public RequiredFieldException() {
		super();
	}
	

	public RequiredFieldException(String message, String fieldName) {
		super(message);
	}
}

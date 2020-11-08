package com.godofwibu.narga.utils;

import lombok.Getter;
import lombok.Setter;

public class RequiredParameterException extends RuntimeException {
	@Setter
	@Getter
	private String fieldName;
	public RequiredParameterException() {
		super();
	}
	

	public RequiredParameterException(String message, String fieldName) {
		super(message);
	}
}

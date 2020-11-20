package com.godofwibu.narga.utils;

import lombok.Getter;
import lombok.Setter;

public class NoSuchParameterException extends RuntimeException {
	@Setter
	@Getter
	private String fieldName;
	public NoSuchParameterException() {
		super();
	}
	

	public NoSuchParameterException(String message, String fieldName) {
		super(message);
	}
}

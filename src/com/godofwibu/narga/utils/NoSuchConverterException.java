package com.godofwibu.narga.utils;

import lombok.Getter;
import lombok.Setter;

public class NoSuchConverterException extends RuntimeException {
	
	@Setter
	@Getter
	private Class<?> type;

	public NoSuchConverterException(Class<?> type) {
		super("No such converter for " + type.getName());
		this.type = type;
	}
	
	

}

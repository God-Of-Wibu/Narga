package com.godofwibu.narga.services;

public class UserCreationException extends ServiceLayerException {
	
	private String userId;
	
	public UserCreationException() {
		super();
	}

	public UserCreationException(String message, String userId) {
		super(message);
		this.userId = userId;
	}

	public UserCreationException(Throwable cause) {
		super(cause);
	}

}

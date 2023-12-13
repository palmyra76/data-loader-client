package com.palmyralabs.palmyra.dataloaderclient.exception;

import lombok.Getter;

@Getter
public class InvalidMappingException extends RuntimeException {
	private static final long serialVersionUID = -7900051080123927624L;
	private final String key;
	
	public InvalidMappingException(String key, String message) {
		super(message);
		this.key = key;
	}
}

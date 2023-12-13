package com.palmyralabs.palmyra.dataloaderclient.exception;

public class ConverterException extends RuntimeException{
	private static final long serialVersionUID = 8772752616109842229L;
	
	private final Object value;
	
	public ConverterException(String message, Throwable e, Object value){
		super( message, e);
		this.value = value;
	}

	public ConverterException(String message, Object value){
		super( message);
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
}

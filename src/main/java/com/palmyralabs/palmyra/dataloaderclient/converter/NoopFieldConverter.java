package com.palmyralabs.palmyra.dataloaderclient.converter;

public class NoopFieldConverter implements FieldConverter{
	
	private static final NoopFieldConverter instance = new NoopFieldConverter();
	
	public static NoopFieldConverter instance() {
		return instance;
	}
	
	public Object write(Object value) {
		return value;
	}
	
	public Object parse(Object value) {
		return value;
	}
	
	public void setWriteFormat(String format) {
		
	}
	
	public void setParseFormat(String format) {
		
	}
}

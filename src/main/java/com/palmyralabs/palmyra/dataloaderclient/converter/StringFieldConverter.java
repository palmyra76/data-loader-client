package com.palmyralabs.palmyra.dataloaderclient.converter;

public class StringFieldConverter implements FieldConverter{
	
	private static final StringFieldConverter instance = new StringFieldConverter();
	
	public static StringFieldConverter instance() {
		return instance;
	}
	
	public Object write(Object value) {
		return value;
	}
	
	public Object parse(Object value) {
		if(null == value)
			return value;
		if(value instanceof String)
			return value;
		return value.toString();
	}
	
	public void setWriteFormat(String format) {
		
	}
	
	public void setParseFormat(String format) {
		
	}
}

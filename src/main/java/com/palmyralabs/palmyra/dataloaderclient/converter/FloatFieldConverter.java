package com.palmyralabs.palmyra.dataloaderclient.converter;

public class FloatFieldConverter implements FieldConverter{
	
	private static final FloatFieldConverter instance = new FloatFieldConverter();
	
	public static FloatFieldConverter instance() {
		return instance;
	}
	
	public Object write(Object value) {
		return value;
	}
	
	public Object parse(Object value) {
		return Converter.asFloat(value);
	}
}

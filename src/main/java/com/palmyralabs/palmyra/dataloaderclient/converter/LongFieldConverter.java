package com.palmyralabs.palmyra.dataloaderclient.converter;

public class LongFieldConverter implements FieldConverter{
	
	private static final LongFieldConverter instance = new LongFieldConverter();
	
	public static LongFieldConverter instance() {
		return instance;
	}
	
	public Object write(Object value) {
		return value;
	}
	
	public Object parse(Object value) {
		return Converter.asLong(value);
	}
}

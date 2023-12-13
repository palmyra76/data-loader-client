package com.palmyralabs.palmyra.dataloaderclient.converter;

public class IntegerFieldConverter implements FieldConverter{

private static final IntegerFieldConverter instance = new IntegerFieldConverter();
	
	public static IntegerFieldConverter instance() {
		return instance;
	}

	public Object write(Object value) {
		return value;
	}
	
	public Object parse(Object value) {
		return Converter.asInt(value);
	}
}

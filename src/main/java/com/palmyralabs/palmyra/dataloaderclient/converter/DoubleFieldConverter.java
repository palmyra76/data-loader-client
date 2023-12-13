package com.palmyralabs.palmyra.dataloaderclient.converter;

import com.palmyralabs.palmyra.dataloaderclient.exception.ConverterException;

public class DoubleFieldConverter implements FieldConverter {

	private static final DoubleFieldConverter instance = new DoubleFieldConverter();

	public static DoubleFieldConverter instance() {
		return instance;
	}

	public Object write(Object value) {
		return value;
	}

	public Object parse(Object value) {
		if (value instanceof Double) {
			return (Double) value;
		} else {
			try {
				return Double.valueOf(value.toString());
			} catch (Throwable e) {
				throw new ConverterException(" Error while converting to Double", e);
			}
		}
	}
}

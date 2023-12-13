package com.palmyralabs.palmyra.dataloaderclient.converter;

public class BitFieldConverter implements FieldConverter {

	private static final BitFieldConverter instance = new BitFieldConverter();

	public static BitFieldConverter instance() {
		return instance;
	}

	public Object write(Object value) {
		return value;
	}

	public Object parse(Object value) {
		if (null == value)
			return value;
		if (value instanceof Number)
			return value;
		if (value instanceof Boolean)
			return (Boolean) value ? 1 : 0;

		String sVal = value.toString();

		if (sVal.equalsIgnoreCase("true"))
			return 1;
		try {
			int numValue = Integer.parseInt(sVal);
			return numValue > 0 ? 1 : 0;
		} catch (Throwable t) {
			return 0;
		}
	}
}

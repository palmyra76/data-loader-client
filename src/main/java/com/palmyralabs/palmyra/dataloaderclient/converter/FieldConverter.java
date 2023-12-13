package com.palmyralabs.palmyra.dataloaderclient.converter;

public interface FieldConverter {
	public default Object write(Object value) {
		return value;
	}

	public default Object parse(Object value) {
		return value;
	}

	public default void setFormat(String format) {
		throw new RuntimeException(this.getClass().getName() + " setFormat - Method not implemented ");
	}

	public default String getFormat() {
		return null;
	}
}

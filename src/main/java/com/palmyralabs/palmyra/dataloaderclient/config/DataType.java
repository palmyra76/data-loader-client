package com.palmyralabs.palmyra.dataloaderclient.config;

public enum DataType {
	String("string"), Integer("int"), Long("long"), Float("float"), Double("double"), Date("date"),
	DateTime("datetime");

	private final String type;

	DataType(String t) {
		this.type = t;
	}

	public String getType() {
		return type;
	}

	public static DataType of(String input) {
		for (DataType v : values()) {
			if (v.type.equalsIgnoreCase(input))
				return v;
		}
		return String;
	}
}

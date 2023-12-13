package com.palmyralabs.palmyra.dataloaderclient.config;

import java.time.ZoneId;

import com.palmyralabs.palmyra.dataloaderclient.converter.DateFieldConverter;
import com.palmyralabs.palmyra.dataloaderclient.converter.DateTimeFieldConverter;
import com.palmyralabs.palmyra.dataloaderclient.converter.DoubleFieldConverter;
import com.palmyralabs.palmyra.dataloaderclient.converter.FieldConverter;
import com.palmyralabs.palmyra.dataloaderclient.converter.FloatFieldConverter;
import com.palmyralabs.palmyra.dataloaderclient.converter.IntegerFieldConverter;
import com.palmyralabs.palmyra.dataloaderclient.converter.LongFieldConverter;
import com.palmyralabs.palmyra.dataloaderclient.converter.NoopFieldConverter;
import com.palmyralabs.palmyra.dataloaderclient.converter.StringFieldConverter;

import lombok.Getter;

@Getter
public class FieldMapping {
	private final String name;
	private final int column;

	private DataType dataType;
	private String format;

	private FieldConverter converter;

	public FieldMapping(String key, int column, DataType dataType, String pattern) {
		this.name = key;
		this.column = column;
		this.dataType = dataType;
		this.format = pattern;
		this.converter = getConverter(dataType, pattern);
	}

	public FieldMapping(String key, int column) {
		this(key, column, DataType.String, null);
	}

	public FieldConverter getConverter() {
		return converter;
	}

	private FieldConverter getConverter(DataType dataType, String pattern) {
		switch (dataType) {
		case Date:
			return new DateFieldConverter(pattern, ZoneId.systemDefault());
		case DateTime:
			return new DateTimeFieldConverter(pattern, ZoneId.systemDefault());
		case Double:
			return DoubleFieldConverter.instance();
		case Long:
			return LongFieldConverter.instance();
		case Float:
			return FloatFieldConverter.instance();
		case Integer:
			return IntegerFieldConverter.instance();
		case String:
			return StringFieldConverter.instance();
		default:
			return NoopFieldConverter.instance();
		}
	}

}

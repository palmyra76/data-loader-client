package com.palmyralabs.palmyra.dataloaderclient.converter;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFieldConverter implements FieldConverter {

	private DateTimeFormatter dateFormatter = null;
	private String pattern;
	private static final DateFieldConverter instance = new DateFieldConverter("yyyy-MM-dd", ZoneId.of("Asia/Kolkata"));

	public static DateFieldConverter instance() {
		return instance;
	}

	public DateFieldConverter(String pattern, ZoneId zoneId) {
		this.pattern = pattern;
		dateFormatter = DateTimeFormatter.ofPattern(pattern).withZone(zoneId);
	}

	public Object write(Object value) {
		if (null == value)
			return null;

		if (value instanceof java.util.Date) {
			Instant now = Instant.ofEpochMilli(((java.util.Date) value).getTime());
			return dateFormatter.format(now);
		} else
			return value;
	}

	public Object write(java.util.Date value) {
		if (null == value)
			return null;

		Instant now = Instant.ofEpochMilli(value.getTime());
		return dateFormatter.format(now);

	}

	public Object parse(Object value) {
		if (null == value)
			return null;

		if (value instanceof Date)
			return value;

		String sVal = null;
		if (value instanceof String)
			sVal = (String) value;
		else
			sVal = value.toString();

		if(0 == sVal.length())
			return null;
		
		LocalDate ldt = LocalDate.from(dateFormatter.parse(sVal));
		return Date.valueOf(ldt);
	}

	@Override
	public void setFormat(String format) {
		this.pattern = format;
		dateFormatter = DateTimeFormatter.ofPattern(format);
	}

	@Override
	public String getFormat() {
		return this.pattern;
	}
}

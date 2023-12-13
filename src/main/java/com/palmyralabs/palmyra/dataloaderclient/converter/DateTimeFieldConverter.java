package com.palmyralabs.palmyra.dataloaderclient.converter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFieldConverter implements FieldConverter {

	private DateTimeFormatter dateTimeFormatter = null;
	private String pattern;
	private static final DateTimeFieldConverter instance = new DateTimeFieldConverter("yyyy-MM-dd'T'HH:mm:ssZ",
			ZoneId.of("Asia/Kolkata"));

	public static DateTimeFieldConverter instance() {
		return instance;
	}

	public DateTimeFieldConverter(String pattern, ZoneId zoneId) {
		this.pattern = pattern;
		dateTimeFormatter = DateTimeFormatter.ofPattern(pattern).withZone(zoneId);
	}

	public Object write(Object value) {
		if (null == value)
			return null;

		if (value instanceof java.util.Date) {
			java.util.Date dt = (java.util.Date) value;
			return dateTimeFormatter.format(dt.toInstant());
		} else
			return value;
	}

	public Object parse(Object value) {
		if (null == value)
			return null;

		if (value instanceof Date)
			return value;

		if (value instanceof Long) {
			try {
				java.util.Date date = new java.util.Date((Long) value);
				return date;
			} catch (Exception e) {
			}
		}

		String sVal = null;
		if (value instanceof String)
			sVal = (String) value;
		else
			sVal = value.toString();
		
		if(0 == sVal.length())
			return null;

		ZonedDateTime ldt = ZonedDateTime.from(dateTimeFormatter.parse(sVal));
		return Timestamp.from(ldt.toInstant());		
	}

	@Override
	public void setFormat(String format) {
		this.pattern = format;
		dateTimeFormatter = DateTimeFormatter.ofPattern(format);
	}

	public String getFormat() {
		return this.pattern;
	}
}

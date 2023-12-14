package com.palmyralabs.palmyra.dataloaderclient.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFieldConverter implements FieldConverter {

	private DateTimeFormatter dateFormatter = null;
	private String pattern;
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
	private static final DateFieldConverter instance = new DateFieldConverter(DEFAULT_PATTERN,
			ZoneId.of("Asia/Kolkata"));

	public static DateFieldConverter instance() {
		return instance;
	}

	public DateFieldConverter(String pattern, ZoneId zoneId) {
		this.pattern = null != pattern ? pattern : DEFAULT_PATTERN;
		dateFormatter = DateTimeFormatter.ofPattern(this.pattern).withZone(zoneId);
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

	public Object write(Date value) {
		if (null == value)
			return null;

		Instant now = Instant.ofEpochMilli(value.getTime());
		return dateFormatter.format(now);
	}

	private LocalDate convert(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public Object parse(Object value) {
		if (null == value)
			return null;

		if (value instanceof Date)
			return convert((Date) value);

		String sVal = null;
		if (value instanceof String)
			sVal = (String) value;
		else
			sVal = value.toString();

		if (0 == sVal.length())
			return null;

		return LocalDate.from(dateFormatter.parse(sVal));
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

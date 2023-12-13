package com.palmyralabs.palmyra.dataloaderclient.converter;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeFieldConverter implements FieldConverter{
	
	private DateTimeFormatter timeFormatter = null;
	private String pattern;
	private static final TimeFieldConverter instance = new TimeFieldConverter("HH:mm:ss", ZoneId.of("Asia/Kolkata"));
	
	public static TimeFieldConverter instance() {
		return instance;
	}
	
	public TimeFieldConverter(String pattern, ZoneId zoneId) {
		this.pattern = pattern;
		this.timeFormatter = DateTimeFormatter.ofPattern(pattern).withZone(zoneId);
	}
	
	public Object write(Object value) {
		if(null == value)
			return null;
		
		if(value instanceof java.util.Date) {
			Instant now = Instant.ofEpochMilli(((java.util.Date) value).getTime());			
			return timeFormatter.format(now);
		}
		return value;
	}
	
	public Object parse(Object value) {
		if(null == value)
			return null;
		
		if(value instanceof Date)
			return value;
		
		String sVal = null;
		if(value instanceof String) 
			sVal = (String)value;
		else
			sVal = value.toString();
		
		if(0 == sVal.length())
			return null;
		
		LocalTime ldt = LocalTime.from(timeFormatter.parse(sVal));
		return Time.valueOf(ldt);
	}
	
	@Override
	public void setFormat(String format) {
		this.pattern = format;
		timeFormatter = DateTimeFormatter.ofPattern(format);
	}
	@Override
	public String getFormat() {
		return this.pattern;
	}
}

package com.palmyralabs.palmyra.dataloaderclient.converter;


import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;

public class EpochTimeConverter implements FieldConverter{

	private static final EpochTimeConverter instance = new EpochTimeConverter();
	
	private EpochTimeConverter() {
		
	}
	
	public static EpochTimeConverter instance() {
		return instance;
	}
	
	public Object write(Object value) {
		if (null == value)
			return null;
		
		if (value instanceof Date) {
			return ((Date) value).getTime();	  
		} else
			return value;
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
		
		Long lVal = Long.parseLong(sVal);
		
		LocalTime ldt = LocalTime.from(Instant.ofEpochMilli(lVal)); 		
		return Time.valueOf(ldt);
	}
}

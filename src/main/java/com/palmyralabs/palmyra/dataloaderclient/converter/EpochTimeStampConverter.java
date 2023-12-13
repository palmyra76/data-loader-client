package com.palmyralabs.palmyra.dataloaderclient.converter;


import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

public class EpochTimeStampConverter implements FieldConverter{

	private static final EpochTimeStampConverter instance = new EpochTimeStampConverter();
	
	private EpochTimeStampConverter() {
		
	}
	
	public static EpochTimeStampConverter instance() {
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
		
		ZonedDateTime ldt = ZonedDateTime.from(Instant.ofEpochMilli(lVal));
		return Timestamp.from(ldt.toInstant());		
	}
}

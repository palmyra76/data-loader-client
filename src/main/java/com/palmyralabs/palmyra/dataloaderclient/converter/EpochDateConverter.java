package com.palmyralabs.palmyra.dataloaderclient.converter;


import java.time.LocalDate;
import java.util.Date;

public class EpochDateConverter implements FieldConverter{

	private static final EpochDateConverter instance = new EpochDateConverter();
	
	private EpochDateConverter() {
		
	}
	
	public static EpochDateConverter instance() {
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
		LocalDate ldt = LocalDate.ofEpochDay(lVal);		
		return java.sql.Date.valueOf(ldt);
	}
}

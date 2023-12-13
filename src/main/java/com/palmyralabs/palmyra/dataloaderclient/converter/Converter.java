package com.palmyralabs.palmyra.dataloaderclient.converter;

import java.math.BigDecimal;

public class Converter {
	public final static BigDecimal asDecimal(Object obj) {
		if (null == obj)
			return null;
		else if (obj instanceof BigDecimal)
			return (BigDecimal) obj;

		if (obj instanceof Double)
			return new BigDecimal((Double) obj);

		String sVal = obj.toString();
		if (0 == sVal.length())
			return null;
		return new BigDecimal(Double.valueOf(sVal));
	}

	public final static Double asDouble(Object obj) {
		if (null == obj)
			return null;
		if (obj instanceof Double)
			return (Double) obj;
		String sVal = obj.toString();
		if (0 == sVal.length())
			return null;
		return Double.parseDouble(sVal);
	}

	public final static Float asFloat(Object obj) {
		if (null == obj)
			return null;
		else if (obj instanceof Float)
			return (Float) obj;

		String sVal = obj.toString();
		if (0 == sVal.length())
			return null;
		return Float.parseFloat(sVal);
	}

	public final static Integer asInt(Object obj) {
		if (null == obj)
			return null;
		else if (obj instanceof Integer)
			return (Integer) obj;
		else if (obj instanceof Float || obj instanceof Double) {
			double v = (double) obj;
			if (v == (int) (v))
				return (int) v;
		}
		String sVal = obj.toString();
		if (0 == sVal.length())
			return null;
		return Integer.parseInt(sVal);
	}

	public final static Long asLong(Object obj) {
		if (null == obj)
			return null;
		else if (obj instanceof Long)
			return (Long) obj;

		if (obj instanceof Float || obj instanceof Double) {
			double v = (double) obj;
			if (v == (long) (v))
				return (long) v;
		}

		String sVal = obj.toString();
		if (0 == sVal.length())
			return null;
		return Long.parseLong(sVal);
	}

	public final static String asString(Object obj) {
		if (null == obj)
			return null;
		else if (obj instanceof String)
			return (String) obj;
		else
			return obj.toString();
	}
}

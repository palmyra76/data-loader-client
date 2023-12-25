package com.palmyralabs.palmyra.dataloaderclient.reader;

/**
 * @author ksvraja
 *
 */
public final class TextUtil {

	public static String snakeCase(String column) {
		char[] org = column.trim().toCharArray();
		char[] result = new char[org.length * 2];
		char c;
		int j = 0;
		int length = org.length;
		for (int i = 0; i < length; i++, j++) {
			c = org[i];
			if (Character.isUpperCase(c)) {
				if (j > 0 && result[j - 1] != '_')
					result[j++] = '_';
				c = Character.toLowerCase(c);
			}
			if (c == ' ') {
				if (result[j - 1] != '_')
					c = '_';
				else {
					j--;
					continue;
				}
			}
			result[j] = c;
		}
		return new String(result, 0, j);
	}

	public static String camelCase(String column) {
		char[] result = new char[column.length()];
		char[] org = column.toCharArray();
		char c;
		int j = 0;
		int length = org.length;
		for (int i = 0; i < length; i++, j++) {
			c = org[i];
			if (c == '_' || c == ' ') {
				i++;
				if (i < length)
					c = Character.toUpperCase(org[i]);
			} else {
				c = Character.toLowerCase(c);
			}
			result[j] = c;
		}
		return new String(result, 0, j);
	}

	public static String camelCaseFirstLetterUpperCase(String column) {
		char[] org = column.toCharArray();

		int j = 1;
		int i = 1;

		int length = org.length - 1;
		org[0] = Character.toUpperCase(org[0]);

		for (i = 1; i < length; i++, j++) {
			if (org[i] == '_' || org[i] == ' ') {
				i++;
				org[j] = Character.toUpperCase(org[i]);
			} else {
				org[j] = Character.toLowerCase(org[i]);
			}
		}

		if (i == length) {
			if ('_' != org[i] && ' ' != org[i]) {
				org[j++] = (org[i - 1] == '_') ? Character.toUpperCase(org[i]) : Character.toLowerCase(org[i]);
			}
		}

		return new String(org, 0, j);
	}

	public static String initCase(String column) {
		char[] org = column.toCharArray();
		int length = org.length - 1;

		org[0] = Character.toUpperCase(org[0]);
		int i = 1;
		for (i = 1; i < length; i++) {
			if (org[i] == '_' || org[i] == ' ') {
				org[i++] = ' ';
				org[i] = Character.toUpperCase(org[i]);
			} else
				org[i] = Character.toLowerCase(org[i]);
		}

		if (i == length) {
			if ('_' == org[i])
				org[i] = ' ';
			else
				org[i] = (org[i - 1] == '_') ? Character.toUpperCase(org[i]) : Character.toLowerCase(org[i]);
		}

		return new String(org);
	}

	public static boolean contains(String value, char ch) {
		return value.indexOf(ch, 0) >= 0;
	}

	public static String replaceAll(String oldVal, char oldChar, char newChar) {
		char[] val = oldVal.toCharArray();
		if (oldChar != newChar) {
			int len = val.length;
			int i = -1;

			while (++i < len) {
				if (val[i] == oldChar) {
					val[i] = newChar;
				} else
					val[i] = val[i];
			}
			return new String(val);
		}
		return oldVal;
	}

	public static String replaceAll(String oldVal, char oldChar1, char newChar1, char oldChar2, char newChar2) {
		char[] val = oldVal.toCharArray();
		int len = val.length;
		int i = -1;
//		char[] val = value;
//		char buf[] = new char[len];

		while (++i < len) {
			if (val[i] == oldChar1) {
				val[i] = newChar1;
			} else if (val[i] == oldChar2) {
				val[i] = newChar2;
			} else
				val[i] = val[i];
		}
		return new String(val);
	}

	public static String getLast(String value, char delimiter, int count) {
		char[] source = value.toCharArray();
		int len = source.length;

		int i = -1;
		int cntIndex = 0;

		while (++i < len && cntIndex < count) {
			if (source[i] == delimiter) {
				cntIndex++;
			}
		}
		if (cntIndex >= count)
			return value.substring(i);
		else
			throw new RuntimeException(cntIndex + " Occurrence of " + delimiter + " is in " + value);
	}

	public static String getFirst(String value, char delimiter, int count) {
		char[] source = value.toCharArray();
		int len = source.length;

		int i = -1;
		int cntIndex = 0;

		while (++i < len && cntIndex < count) {
			if (source[i] == delimiter) {
				cntIndex++;
			}
		}

		if (i == len || cntIndex == count - 1) {
			return value;
		}

		if (cntIndex >= count)
			return value.substring(0, i);
		else
			throw new RuntimeException(cntIndex + " Occurrence of " + delimiter + " is in " + value);
	}

	public static String trim(String oldVal, char oldChar) {
		char[] val = oldVal.toCharArray();

		int len = val.length;
		int i = -1;

		char buf[] = new char[len];
		int j = 0;
		while (++i < len) {
			if (val[i] == oldChar) {
				continue;
			} else
				buf[j++] = val[i];
		}
		return new String(buf, 0, j);
	}

//	public static String replaceLast(char delimiter, String value, String suffix) {
//		int idx = value.lastIndexOf(delimiter);
//		StringBuilder builder = StringBuilderCache.get();
//		if (idx < 0) {
//			builder.append(value);
//		} else {
//			builder.append(value.substring(0, idx));
//		}
//		builder.append(delimiter).append(suffix);
//		return StringBuilderCache.release(builder);
//	}

	public static String getFirst(String value, char delimiter) {
		int idx = value.indexOf(delimiter);
		if (idx > 0) {
			return value.substring(0, idx);
		} else
			return value;
	}

	public static String trimLast(String value, char delimiter) {
		int idx = value.lastIndexOf(delimiter);
		if (idx > 0) {
			return value.substring(0, idx);
		} else
			return value;
	}

	public static String trimFirst(String value, char delimiter) {
		int idx = value.indexOf(delimiter);
		if (idx > 0) {
			return value.substring(idx + 1, value.length());
		} else
			return value;
	}

//	public static String replaceLast(String value, String suffix, char delimiter) {
//		int idx = value.lastIndexOf(delimiter);
//		StringBuilder sb = StringBuilderCache.get();
//
//		if (idx > 0) {
//			sb.append(value, 0, idx + 1).append(suffix).toString();
//		} else
//			sb.append(value).append(delimiter).append(suffix).toString();
//
//		return StringBuilderCache.release(sb);
//	}

	public static String getLast(String value, char delimiter) {
		int idx = value.lastIndexOf(delimiter);
		return value.substring(idx + 1, value.length());
	}

	public static boolean startsWith(String value, char c) {
		return value.charAt(0) == c;
	}

	public static boolean endsWith(String value, char c) {
		return value.charAt(value.length() - 1) == c;
	}

	public static String extractNumber(String column, boolean fetchConsecutive) {
		char[] org = column.trim().toCharArray();
		char[] result = new char[org.length];
		boolean dotFound = false;

		int j = 0;
		for (char c : org) {
			if (c > 47 && c < 58) {
				result[j++] = c;
			} else if (c == 46) {
				if (dotFound) {
					if (fetchConsecutive)
						break;
					else
						continue;
				}
				dotFound = true;
				result[j++] = c;

			} else if (fetchConsecutive) {
				if (j > 0)
					break;
			}
		}
		if (j > 0)
			if (result[j - 1] == 46)
				return new String(result, 0, j - 1);
			else
				return new String(result, 0, j);
		else
			return null;
	}
}
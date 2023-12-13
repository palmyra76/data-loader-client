package com.palmyralabs.palmyra.dataloaderclient.config;

import java.io.InputStream;
import java.util.Properties;

import com.palmyralabs.palmyra.dataloaderclient.exception.InvalidMappingException;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MappingReader {

	public DataloadMapping getMapping(String fileName) {
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
		return getMapping(inStream);
	}

	@SneakyThrows
	public DataloadMapping getMapping(InputStream is) {
		Properties props = new Properties();
		props.load(is);
		return getMapping(props);
	}

	private DataloadMapping getMapping(Properties props) {
		DataloadMapping result = new DataloadMapping();

		for (String key : props.stringPropertyNames()) {
			String value = props.getProperty(key);
			FieldMapping mapping = createMapping(key, value);
			result.addMapping(mapping);
		}
		return result;
	}

	private FieldMapping createMapping(String key, String value) {
		int v = Integer.parseInt(value);
		int idx = key.indexOf(':');
		int i = key.indexOf(';');
		if (idx < i) {
			log.error("Invalid key {}", key);
			throw new InvalidMappingException(key, "Invalid key");
		}
		if (idx > 0) {
			String name = key.substring(0, idx);
			if (i > 0) {
				String dataType = key.substring(idx, i);
				String pattern = key.substring(i);
				return new FieldMapping(name, v, DataType.of(dataType), pattern);
			} else {
				String dataType = key.substring(idx);
				return new FieldMapping(name, v, DataType.of(dataType), null);
			}

		} else {
			return new FieldMapping(key, v);
		}
	}
}

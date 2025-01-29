package com.palmyralabs.palmyra.dataloaderclient.config;

import java.io.InputStream;
import java.util.Properties;

import com.palmyralabs.palmyra.dataloaderclient.MappingReader;
import com.palmyralabs.palmyra.dataloaderclient.exception.InvalidMappingException;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassPathMappingReader implements MappingReader{
	
	private final String baseFolder;
	private final String extension;

	public ClassPathMappingReader(String baseFolder) {
		this(baseFolder, "properties");
	}
	
	public ClassPathMappingReader(String baseFolder, String extension) {
		String slash = baseFolder.endsWith("/") ? "" : "/";				
		this.baseFolder = baseFolder + slash;
		this.extension = extension;
	}

	public DataloadMapping getMapping(String reference) {
		String fileName = baseFolder + reference + "." + extension;
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
		if (null == inStream) {
			throw new RuntimeException("file " + fileName + " is not available in classpath");
		}
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
			String field = key.startsWith("^") ? key.substring(1) : key;			
			FieldMapping mapping = createMapping(field, value);
			mapping.setMandatory(key.startsWith("^"));
			result.addMapping(mapping);
		}
		return result;
	}

	private FieldMapping createMapping(String key, String value) {
		int v = Integer.parseInt(value);
		int idx = key.indexOf('-');
		int i = key.indexOf(';');
		
		if (idx > 0) {
			String name = key.substring(0, idx);
			if (i > 0) {
				if (idx < i) {
					String dataType = key.substring(idx + 1, i);
					String pattern = key.substring(i + 1);
					return new FieldMapping(name, v, DataType.of(dataType), pattern);
				} else
					throw new RuntimeException("invalid mapping " + key);
			} else {
				String dataType = key.substring(idx + 1);
				return new FieldMapping(name, v, DataType.of(dataType), null);
			}

		} else {
			return new FieldMapping(key, v);
		}
	}
}

package com.palmyralabs.palmyra.dataloaderclient.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataloadMapping {
	private List<FieldMapping> fieldMapping = new ArrayList<FieldMapping>();

	public void addMapping(FieldMapping mapping) {
		this.fieldMapping.add(mapping);
	}
}

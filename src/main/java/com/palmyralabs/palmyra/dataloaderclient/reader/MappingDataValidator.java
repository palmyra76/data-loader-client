package com.palmyralabs.palmyra.dataloaderclient.reader;

import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.dataloaderclient.config.DataloadMapping;
import com.palmyralabs.palmyra.dataloaderclient.config.FieldMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MappingDataValidator implements DataValidator{

	private final DataloadMapping dataMapping;
	
	@Override
	public void checkValid(Tuple data) {
		for (FieldMapping mapping : dataMapping.getFieldMapping()) {
			String key = mapping.getName();
			Object value = data.getRefAttribute(key);
			if(null == value && mapping.isMandatory()) {
				throw new RuntimeException("Mandatary attribute is missing " + mapping.getName());
			}
		}
	}

}

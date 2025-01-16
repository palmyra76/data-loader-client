package com.palmyralabs.palmyra.dataloaderclient;

import com.palmyralabs.palmyra.dataloaderclient.config.DataloadMapping;

public interface MappingReader {

	public DataloadMapping getMapping(String reference);

}

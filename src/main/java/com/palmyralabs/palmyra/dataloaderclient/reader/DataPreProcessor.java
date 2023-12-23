package com.palmyralabs.palmyra.dataloaderclient.reader;

import com.palmyralabs.palmyra.client.Tuple;

public interface DataPreProcessor {
	Tuple preProcess(Tuple data);
	
	DataPreProcessor NOOP_PROCESSOR = new DataPreProcessor() {
		
		@Override
		public Tuple preProcess(Tuple data) {		
			return data;
		}
	};
}

package com.palmyralabs.palmyra.dataloaderclient.reader;

import com.palmyralabs.palmyra.client.Tuple;

public interface DataValidator {
	void checkValid(Tuple data);
	
	DataValidator NOOP_VALIDATOR = new DataValidator() {
		@Override
		public void checkValid(Tuple data) {
			
		}
	};
}

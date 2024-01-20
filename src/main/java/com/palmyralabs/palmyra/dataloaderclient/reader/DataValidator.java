package com.palmyralabs.palmyra.dataloaderclient.reader;

import com.palmyralabs.palmyra.client.Tuple;

public interface DataValidator {
	void checkValid(Tuple data);
}

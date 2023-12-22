package com.palmyralabs.palmyra.dataloaderclient.writer;

import com.palmyralabs.palmyra.dataloaderclient.model.ErrorMessage;

public class ExcelErrorMessage<T> extends ErrorMessage<T>{

	public ExcelErrorMessage(T errorData, String errorType) {
		super(errorData, errorType);
	}

}

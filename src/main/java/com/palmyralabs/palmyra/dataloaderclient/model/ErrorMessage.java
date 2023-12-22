package com.palmyralabs.palmyra.dataloaderclient.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage<T> {
	
	private final T errorData;
    private final String errorType;
    

    public ErrorMessage(T errorData, String errorType) {
        this.errorData = errorData;
        this.errorType = errorType;
    }

}




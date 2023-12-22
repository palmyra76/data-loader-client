package com.palmyralabs.palmyra.dataloaderclient.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorMessage<T> {
	private final int rowNumber;
	private final T errorData;
    private final String errorType;
    private final Throwable t;    
}
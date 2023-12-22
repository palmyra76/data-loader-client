package com.palmyralabs.palmyra.dataloaderclient.writer;

import java.io.Closeable;
import java.util.function.Consumer;

import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.dataloaderclient.model.ErrorMessage;

public interface ErrorWriter extends Consumer<ErrorMessage<Tuple>>, Closeable{
	void initialize();
}

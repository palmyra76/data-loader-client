package com.palmyralabs.palmyra.dataloaderclient.reader;

import java.io.Closeable;

import com.palmyralabs.palmyra.client.Tuple;

public interface DataReader extends Iterable<Tuple>, Closeable{
	public String getName();
}

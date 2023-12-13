package com.palmyralabs.palmyra.dataloaderclient;

import java.io.IOException;

import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.client.TupleRestClient;
import com.palmyralabs.palmyra.dataloaderclient.reader.DataReader;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Loader {	
	private final TupleRestClient tupleClient;
	private int loadedRecords = 0;
	private int errorRecords = 0;

	public Loader(TupleRestClient tupleClient) {		
		this.tupleClient = tupleClient;
	}

	@SneakyThrows
	public synchronized void loadData(DataReader reader) {
		for (Tuple data : reader) {
			try {
				tupleClient.save(data);
				loadedRecords++;
			} catch (IOException e) {
				log.error("Error while loading data from source - " + reader.getName(), e);
				errorRecords++;
			}
		}
		reader.close();
	}
}

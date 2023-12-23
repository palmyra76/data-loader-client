package com.palmyralabs.palmyra.dataloaderclient;

import java.io.IOException;

import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.client.TupleRestClient;
import com.palmyralabs.palmyra.dataloaderclient.model.ErrorMessage;
import com.palmyralabs.palmyra.dataloaderclient.reader.DataPreProcessor;
import com.palmyralabs.palmyra.dataloaderclient.reader.DataReader;
import com.palmyralabs.palmyra.dataloaderclient.writer.ErrorWriter;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Loader {
	private final TupleRestClient tupleClient;
	private final DataPreProcessor preProcessor;

	private int loadedRecords = 0;
	private int errorRecords = 0;

	public Loader(TupleRestClient tupleClient) {
		this(tupleClient, DataPreProcessor.NOOP_PROCESSOR);
	}

	public Loader(TupleRestClient tupleClient, DataPreProcessor preProcessor) {
		this.tupleClient = tupleClient;
		this.preProcessor = preProcessor;
	}

	@SneakyThrows
	public synchronized void loadData(DataReader reader, ErrorWriter errorWriter) {
		int rowNumber = 0;
		int emptyRecords = 0;

		for (Tuple data : reader) {
			if (0 == data.getAttributes().size()) {
				if (emptyRecords > 5)
					break;
				log.error("Empty Data");
				emptyRecords++;
				continue;
			} else {
				emptyRecords = 1;
			}
			try {
				tupleClient.save(preProcessor.preProcess(data));
				loadedRecords++;
			} catch (IOException e) {
				errorWriter.accept(new ErrorMessage<Tuple>(rowNumber, data, "loadError", e));
			}
		}
		log.info("loaded {} records from {}", loadedRecords, reader.getName());
		errorWriter.close();
		reader.close();
	}

}

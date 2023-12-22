package com.palmyralabs.palmyra.dataloaderclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.client.TupleRestClient;
import com.palmyralabs.palmyra.dataloaderclient.model.ErrorMessage;
import com.palmyralabs.palmyra.dataloaderclient.reader.DataReader;
import com.palmyralabs.palmyra.dataloaderclient.writer.ErrorWriter;

import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class Loader {
    private final TupleRestClient tupleClient;
    private int loadedRecords = 0;
    private int errorRecords = 0;

    public Loader(TupleRestClient tupleClient) {
        this.tupleClient = tupleClient;
    }

    @SneakyThrows
    public synchronized void loadData(DataReader reader, ErrorWriter errorWriter) {
    	int rowNumber = 0;
    	
        for (Tuple data : reader) {
            if (0 == data.getAttributes().size()) {
                System.out.println("empty tuple");
            }
            try {
                tupleClient.save(data);
                loadedRecords++;
            } catch (IOException e) {
            	errorWriter.accept(new ErrorMessage<Tuple>(rowNumber, data, "loadError", e));                
            }
        }
        errorWriter.close();
        reader.close();
    }


}
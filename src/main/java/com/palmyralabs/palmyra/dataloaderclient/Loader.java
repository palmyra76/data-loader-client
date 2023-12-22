package com.palmyralabs.palmyra.dataloaderclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.client.TupleRestClient;
import com.palmyralabs.palmyra.dataloaderclient.model.ErrorMessage;
import com.palmyralabs.palmyra.dataloaderclient.reader.DataReader;
import com.palmyralabs.palmyra.dataloaderclient.writer.ErrorWriter;
import com.palmyralabs.palmyra.dataloaderclient.writer.ExcelErrorMessage;

import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class Loader {
    private final TupleRestClient tupleClient;
    private int loadedRecords = 0;
    private int errorRecords = 0;
    private List<ExcelErrorMessage<?>> errorMessages = new ArrayList<>();

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

//    private void handleLoadError(DataReader reader, Tuple data, IOException e) {
//        errorRecords++;
//
//
//        String errorMessage = "Error while loading data from source - " + reader.getName() + ": " + e.getMessage();
//        ExcelErrorMessage<String> excelErrorMessage = new ExcelErrorMessage<>(errorMessage, "ExampleErrorType");
//        errorMessages.add(excelErrorMessage);
//    }
}

package com.palmyralabs.palmyra.dataloaderclient.test;

import java.io.File;

import com.palmyralabs.palmyra.client.TupleRestClient;
import com.palmyralabs.palmyra.dataloaderclient.Loader;
import com.palmyralabs.palmyra.dataloaderclient.PalmyraClientFactory;
import com.palmyralabs.palmyra.dataloaderclient.config.DataloadMapping;
import com.palmyralabs.palmyra.dataloaderclient.config.MappingReader;
import com.palmyralabs.palmyra.dataloaderclient.reader.ExcelDataReader;
import com.palmyralabs.palmyra.dataloaderclient.writer.ErrorWriter;
import com.palmyralabs.palmyra.dataloaderclient.writer.ExcelErrorWriter;

import lombok.SneakyThrows;

public class Main {
	private static final MappingReader mappingReader = new MappingReader();

	public static void main(String[] args) {
	    TupleRestClient tupleClient = PalmyraClientFactory.getClient("http://localhost:6060", "example");
	    Loader loader = new Loader(tupleClient);
	    MappingReader mappingReader = new MappingReader(); 
	    loadData(loader, mappingReader, "example.properties", "C:\\Users\\Aravindhan\\OneDrive\\Desktop\\dataload.xlsx");
	}

	@SneakyThrows
	private static void loadData(Loader loader, MappingReader mappingReader, String mappingFile, String sourceFile) {
	    DataloadMapping dataMapping = mappingReader.getMapping("mapping/" + mappingFile);
	    ExcelDataReader dataReader = new ExcelDataReader(dataMapping, "example");

	    int sheetIndex = 0;

	    
	    ExcelErrorWriter errorWriter = new ExcelErrorWriter();
	    errorWriter.initialize(); 

	    loader.loadData(dataReader.readSheet(new File(sourceFile), sheetIndex, 1), errorWriter);
	}

}
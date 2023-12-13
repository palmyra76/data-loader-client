package com.palmyralabs.palmyra.dataloaderclient;

import java.io.File;

import com.palmyralabs.palmyra.client.TupleRestClient;
import com.palmyralabs.palmyra.dataloaderclient.config.DataloadMapping;
import com.palmyralabs.palmyra.dataloaderclient.config.MappingReader;
import com.palmyralabs.palmyra.dataloaderclient.reader.ExcelDataReader;

public class Main {
	private static final MappingReader mappingReader = new MappingReader();

	public static void main(String[] args) {
		TupleRestClient tupleClient = PalmyraClientFactory.getClient("http://localhost:6060", "example");
		Loader loader = new Loader(tupleClient);
		loadData(loader, "example.properties", "data.xlsx");
	}

	private static void loadData(Loader loader, String mappingFile, String sourceFile) {
		DataloadMapping dataMapping = mappingReader.getMapping("mapping/" + mappingFile);
		ExcelDataReader dataReader = new ExcelDataReader(dataMapping, "example");
		loader.loadData(dataReader.readSheet(new File(sourceFile)));
	}

}

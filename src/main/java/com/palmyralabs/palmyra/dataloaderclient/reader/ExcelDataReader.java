package com.palmyralabs.palmyra.dataloaderclient.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.client.pojo.TupleImpl;
import com.palmyralabs.palmyra.dataloaderclient.config.DataloadMapping;
import com.palmyralabs.palmyra.dataloaderclient.config.FieldMapping;
import com.palmyralabs.palmyra.dataloaderclient.converter.FieldConverter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Setter
@Getter
@Slf4j
public class ExcelDataReader {
	private final DataloadMapping dataMapping;
	private final String name;
	private int sheetNumber = 0;
	private int startRow = 1;

	private final DataFormatter formatter = new DataFormatter();

	private Tuple readRow(Row row) {

		Tuple tuple = new TupleImpl();

		for (FieldMapping mapping : dataMapping.getFieldMapping()) {
			String key = mapping.getName();
			Cell cell = row.getCell(mapping.getColumn());
			if (null != cell) {
				Object value = readValue(cell);
				tuple.setRefAttribute(key, formatValue(mapping, value));
			}
		}
		return tuple;
	}

	private Object readValue(Cell cell) {
		CellType cellType = cell.getCellType();
		switch (cellType) {
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell, null)) {
				return cell.getDateCellValue();
			} else {
				return formatter.formatCellValue(cell).trim();
			}
		case BLANK:
			return null;
		default:
			return formatter.formatCellValue(cell).trim();
		}
	}

	public static Object formatValue(FieldMapping mapping, Object val) {

		if (null == val)
			return null;
		try {
			FieldConverter converter = mapping.getConverter();
			return converter.parse(val);
		} catch (NumberFormatException nfe) {
			log.error("Invalid number {} for {}", val, mapping.getName());
			return null;
		} catch(DateTimeParseException dpe) {
			log.error("Invalid date {} for {}", val, mapping.getName());
			return null;
		}
	}

	public DataReader readSheet(File sourceFile) {
		return readSheet(sourceFile, sheetNumber, startRow);
	}

	@SneakyThrows
	public DataReader readSheet(File sourceFile, int sheetNumber, int startRow) {
		if (sourceFile.exists()) {
			Workbook workbook;
			try {
				workbook = new XSSFWorkbook(sourceFile);
			} catch (Throwable t) {
				FileInputStream fis = new FileInputStream(sourceFile);
				workbook = new HSSFWorkbook(fis);
			}
			return new FileIterable(workbook, sheetNumber, startRow);
		} else {
			throw new FileNotFoundException("File not found '" + sourceFile.toString() + "'");
		}
	}

	class FileIterable implements DataReader {
		private final Workbook workbook;
		private final int sheetNumber;
		private final int startRow;

		public FileIterable(Workbook workbook, int sheetno, int startrow) {
			this.workbook = workbook;
			this.sheetNumber = sheetno;
			this.startRow = startrow;
		}

		@Override
		public Iterator<Tuple> iterator() {
			return new fileIterator(workbook, sheetNumber, startRow);
		}

		@Override
		public void close() throws IOException {
			workbook.close();
		}

		@Override
		public String getName() {
			return name;
		}

	}

	class fileIterator implements Iterator<Tuple> {

		private Iterator<Row> rowIterator;
		private int currentRow = 0;

		public fileIterator(Workbook workbook, int sheetNo, int startRow) {
			Sheet sheet = workbook.getSheetAt(sheetNo);
			rowIterator = sheet.iterator();

			while (rowIterator.hasNext() && currentRow < startRow) {
				currentRow++;
				rowIterator.next();
			}
		}

		@Override
		public boolean hasNext() {
			return rowIterator.hasNext();
		}

		@Override
		public Tuple next() {
			Row row = rowIterator.next();
			return readRow(row);
		}
	}
}

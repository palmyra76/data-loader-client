package com.palmyralabs.palmyra.dataloaderclient.writer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.dataloaderclient.config.DataloadMapping;
import com.palmyralabs.palmyra.dataloaderclient.config.FieldMapping;
import com.palmyralabs.palmyra.dataloaderclient.model.ErrorMessage;

public class ExcelErrorWriter implements ErrorWriter {

    private final DataloadMapping dataMapping;
    private final Workbook workbook;
    private final Sheet sheet;
    private int rowNum;
    private final String existingFilePath;

    public ExcelErrorWriter(DataloadMapping dataMapping, String existingFilePath) {
        this.dataMapping = dataMapping;
        this.rowNum = 1;
        this.existingFilePath = existingFilePath;

        this.workbook = readWorkbook(existingFilePath);
        this.sheet = workbook.getSheet("Error Log");

        initialize();
    }

    private Workbook readWorkbook(String filePath) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            return new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading existing workbook: " + e.getMessage());
        }
    }

    public void initialize() {
        if (sheet == null || sheet.getLastRowNum() == 0) {
            throw new RuntimeException("Error: Unable to find 'Error Log' sheet or sheet is empty.");
        }
        
    }

    @Override
    public void accept(ErrorMessage<Tuple> errorMessage) {
        Row row = sheet.createRow(rowNum);
        Tuple errorData = errorMessage.getErrorData();

        Cell rowNumberCell = row.createCell(0);
        rowNumberCell.setCellValue(sheet.getLastRowNum() + 1);

        int cellIndex = 1;

        for (FieldMapping mapping : dataMapping.getFieldMapping()) {
            String key = mapping.getName();
            Cell cell = row.createCell(cellIndex);

            Object value = errorData.getAttributes().get(key);

            if (value instanceof Date) {
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) value));
            } else {
                cell.setCellValue(value != null ? value.toString() : "");
            }

            cellIndex++;
        }

        Cell errorTypeCell = row.createCell(cellIndex);
        errorTypeCell.setCellValue(errorMessage.getT().getMessage());

        rowNum++;
        writeWorkbookToFile();
    }

    private void writeWorkbookToFile() {
        try (FileOutputStream fileOut = new FileOutputStream(existingFilePath)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error writing to existing workbook: " + e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        System.out.println("Error messages written to: " + existingFilePath);
    }

	
}

package com.palmyralabs.palmyra.dataloaderclient.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.dataloaderclient.model.ErrorMessage;

public class ExcelErrorWriter implements ErrorWriter {

    private Workbook workbook;
    private Sheet sheet;
    private int rowNum;

    public void initialize() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Error Log");

        Row headerRow = sheet.createRow(0);

        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("Error Data");
        Cell headerCell2 = headerRow.createCell(2);
        headerCell2.setCellValue("Line Number");
        Cell headerCell3 = headerRow.createCell(1);
        headerCell3.setCellValue("Error Message");

        rowNum = 1;
    }

    public void accept(ErrorMessage<Tuple> errorMessage) {
        Row row = sheet.createRow(rowNum);
        Tuple errorData = errorMessage.getErrorData();

        int cellIndex = 0;

        for (Map.Entry<String, Object> entry : errorData.getAttributes().entrySet()) {
            Cell cell = row.createCell(cellIndex);
            cell.setCellValue(entry.getValue() != null ? entry.getValue().toString() : "");
            cellIndex++;
        }
        
        Cell rowNumberCell = row.createCell(cellIndex + 1);
        rowNumberCell.setCellValue(sheet.getLastRowNum() + 1);
        
        Cell errorTypeCell = row.createCell(cellIndex);
        errorTypeCell.setCellValue(errorMessage.getErrorType());

        rowNum++;
    }


    @Override
    public void close() throws IOException {
    	String outputFilePath = System.getProperty("user.home") + File.separator + "errorlog.xlsx";
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Error messages written to: " + outputFilePath);
    }

}

package com.palmyralabs.palmyra.dataloaderclient.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("Line Number");

        rowNum = 1;
    }

    public void accept(ErrorMessage<Tuple> errorMessage) {
        Row row = sheet.createRow(rowNum);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue(errorMessage.getErrorData().toString());
        Cell cell2 = row.createCell(1);
        cell2.setCellValue(sheet.getLastRowNum() + 1);

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

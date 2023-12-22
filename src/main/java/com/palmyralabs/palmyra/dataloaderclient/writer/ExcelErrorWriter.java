package com.palmyralabs.palmyra.dataloaderclient.writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.dataloaderclient.model.ErrorMessage;

public class ExcelErrorWriter implements ErrorWriter{
	

	/**
	 * Write one row per error received
	 */
	@Override
	public void accept(ErrorMessage<Tuple> errorMessage) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Close the excel sheet / file here
	 */
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * DO the excel sheet open / other things here
	 */
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
    public static void writeErrors(List<? extends ExcelErrorMessage<?>> errorMessages, String outputFilePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Error Log");

        Row headerRow = sheet.createRow(0);
        
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("Error Data");
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("Line Number");

        for (int i = 0; i < errorMessages.size(); i++) {
            ErrorMessage<?> errorMessage = errorMessages.get(i);
            Row row = sheet.createRow(i + 1);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(errorMessage.getErrorData().toString());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(getLineNumber());
        }

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

    private static int getLineNumber() {
        // Use the stack trace to get the line number
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length >= 4) {
            return stackTrace[3].getLineNumber();
        } else {
            return -1; // Unable to determine line number
        }
    }
}

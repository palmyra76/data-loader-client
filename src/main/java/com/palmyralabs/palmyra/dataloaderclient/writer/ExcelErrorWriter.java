package com.palmyralabs.palmyra.dataloaderclient.writer;

import java.io.File;
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

    public ExcelErrorWriter(DataloadMapping dataMapping) {
        this.dataMapping = dataMapping;
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("Error Log");
        this.rowNum = 1; 
    }

    @Override
    public void initialize() {
        Row headerRow = sheet.createRow(0);

        Cell headerCell1 = headerRow.createCell(1);
        headerCell1.setCellValue("Error Data");
        Cell headerCell2 = headerRow.createCell(0);
        headerCell2.setCellValue("Line Number");
        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("Error Message");
        
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

            Object value = errorData.getRefAttribute(key);

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
    }
}

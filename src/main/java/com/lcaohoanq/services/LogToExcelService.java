package com.lcaohoanq.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class LogToExcelService {

    private static final String LOG_FILE = "logs/app.log";
    private static final String EXCEL_FILE = "logs/export/app.xlsx";

    public void generateExcelFromLogs() {
        try {
            ensureExportFolderExists();
            BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE));
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Log Data");

            // Create a header row style
            CellStyle headerStyle = createHeaderCellStyle(workbook);

            // Creating the header row
            Row headerRow = sheet.createRow(0);
            createStyledCell(headerRow, 0, "Timestamp", headerStyle);
            createStyledCell(headerRow, 1, "Message", headerStyle);

            String line;
            int rowIndex = 1;
            while ((line = reader.readLine()) != null) {
                // Assuming log pattern: yyyy-MM-dd HH:mm:ss - message
                String[] parts = line.split(" - ", 2);
                if (parts.length == 2) {
                    String timestamp = parts[0];
                    String message = parts[1];

                    Row row = sheet.createRow(rowIndex++);
                    createCell(row, 0, timestamp);
                    createCell(row, 1, message);
                }
            }

            // Auto-size columns
            for (int i = 0; i < 2; i++) {
                sheet.autoSizeColumn(i);
            }

            FileOutputStream fileOut = new FileOutputStream(EXCEL_FILE);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            reader.close();

            System.out.println("Log data successfully written to " + EXCEL_FILE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        // Set background color
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // Set bold font
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        // Set text alignment
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private static void createStyledCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private static void createCell(Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
    }

    private void ensureExportFolderExists() {
        File exportFolder = new File("logs/export");
        if (!exportFolder.exists()) {
            exportFolder.mkdirs();
        }
    }
}


package com.hth.guru99bank;

import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	public ExcelUtils(String filePath, String sheetName) throws IOException {
		workbook = new XSSFWorkbook(filePath);
		sheet = workbook.getSheet(sheetName);
	}
	
	public int getRowCount(){
		int rowCount = sheet.getPhysicalNumberOfRows();
		return rowCount;
	}
	public int getColCount(){
		int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
		return colCount;
	}
	public String getCellDataString(int row, int col) {
		XSSFCell cell = sheet.getRow(row).getCell(col);
		DataFormatter dataFormatter = new DataFormatter();
		String cellDataString = dataFormatter.formatCellValue(cell);
//		String cellDataString = sheet.getRow(row).getCell(col).getStringCellValue();
//		DataFormatter dataFormatter = 
		return cellDataString;
	}
}

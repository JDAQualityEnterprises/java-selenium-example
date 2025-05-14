package com.jda.core;

import org.apache.logging.log4j.LogManager;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

public class ExcelUtils {
	private static Logger logger = LogManager.getLogger("Driver");

	private static XSSFSheet ExcelWSheet;
	 
	private static XSSFWorkbook ExcelWBook;

	private static XSSFCell Cell;

	private static XSSFRow Row;

	public static String testDataFolder = "./TestData";
	
	//This method will set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method 
	public static void setExcelFile(String ExcelFileName, String SheetName){
		String Message;
		String Path = null;
		try {
			Path = testDataFolder + "\\" + ExcelFileName;
   			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(Path);
			// Access the required sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			//Set the missing cell policy
			ExcelWBook.setMissingCellPolicy(MissingCellPolicy.CREATE_NULL_AS_BLANK);
			//ExcelWBook.getMissingCellPolicy();
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Message = "Successfully opened Excel worksheet: " + Path + ": " + SheetName;
			logger.info(Message);
			//Reporter.log(Message);
		}
		catch (Exception e){
			Message = "ExcelUtils | setExcelFile | Error opening Excel worksheet: " + Path + "\\" + SheetName; 
			logger.info(Message, e);
			//Reporter.log(Message + e.getMessage());
		}

	}

	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
    public static String getCellData(int ColNum, int RowNum){
		String Message, CellData = null;
		try{
			//Get the row. If row is blank create an empty row of type String and an empty Cell.
			//If we don't do this a Null Pointer Exception is returned!
			Row = ExcelWSheet.getRow(RowNum);
  			if (Row == null) {
  				Row  = ExcelWSheet.createRow(RowNum);
				//Row.createCell(ColNum);
				Row.createCell(ColNum, CellType.STRING);
			}
			Cell = Row.getCell(ColNum, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			//Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			if (Cell == null) {
				//Cell is null so return a blank value
				return "";
			}
			else {
				switch (CellType.STRING) {
					//switch (Cell.getCellTypeEnum()) {
                case STRING:
                	CellData = Cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(Cell)) {
                    	Date dteCellData = Cell.getDateCellValue();
                    	CellData = dteCellData.toString();
                    } else {
                    	Double dblCellData = Cell.getNumericCellValue();
                    	int intCellData = dblCellData.intValue();
                    	CellData = Integer.toString(intCellData);
                    }
                    break;
                case BOOLEAN:
                	boolean bolCellData = Cell.getBooleanCellValue();
                	CellData = String.valueOf(bolCellData);
                    break;
                case FORMULA:
                	CellData = Cell.getCellFormula();
                    break;
                case BLANK:
                    CellData =  "";
                    break;
                default:
                	CellData = Cell.getStringCellValue();	
				}
				
			}
			return CellData;
			
		}
		catch (Exception e){
			Message = "ExcelUtils | getCellData | Error getting data from Column " + ColNum + " & Row " + RowNum; 
			logger.info(Message, e);
			//Reporter.log(Message + e.getMessage());
			return "";

		}

    }

	//This method is to write in the Excel cell, Row num and Col num are the parameters
	public static void setCellData(String ExcelFileName, String Result, int ColNum, int RowNum){
		String Message;
		String Path = null;
		try{
			Path =  testDataFolder + "\\" + ExcelFileName;
			//Get the row. If row is blank create an empty row of type String and an empty Cell.
			//If we don't do this a Null Pointer Exception is returned!
			Row = ExcelWSheet.getRow(RowNum);
  			if (Row == null) {
  				Row  = ExcelWSheet.createRow(RowNum);
				//Row.createCell(ColNum);
				Row.createCell(ColNum, CellType.STRING);
			}
			Cell = Row.getCell(ColNum, MissingCellPolicy.RETURN_BLANK_AS_NULL);		
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
				//Write the value to the Excel file
				FileOutputStream fileOut = new FileOutputStream(Path);
				ExcelWBook.write(fileOut);
				fileOut.flush();
				} 
			else {
				Cell.setCellValue(Result);	
				//Write the value to the Excel file
				FileOutputStream fileOut = new FileOutputStream(Path);
				ExcelWBook.write(fileOut);
				fileOut.flush();
				//fileOut.close();
			}
		}
		catch(Exception e){
			Message = "ExcelUtils | setCellData | Error writing to " + Path + "\r\n'" + Result + "' to Column " + ColNum + " & Row " + RowNum; 
			logger.info(Message, e);
			//Reporter.log(Message + e.getMessage());
		}
	}
	
	public static void closeExcelFile(){
		String Message;
		try {
			ExcelWBook.close();
		} catch (IOException e) {
			Message = "ExcelUtils | closeExcelFile | Error closing Excel file"; 
			logger.info(Message, e);
			//Reporter.log(Message + e.getMessage());
		}
	}
	
	//Overloaded version... 
	public static String getCellData(String file, String workSheet, int RowNum, int ColNum){		
		String Message;
		String cellData = "";
		try {
			ExcelUtils.setExcelFile(file, workSheet);
			cellData = ExcelUtils.getCellData(RowNum, ColNum);	
			ExcelUtils.closeExcelFile();	
			
	
		} catch (Exception e) {
		Message = "ExcelUtils | closeExcelFile | Error closing Excel file"; 
		logger.info(Message, e);
		//Reporter.log(Message + e.getMessage());
		}
		return cellData;
	}


}

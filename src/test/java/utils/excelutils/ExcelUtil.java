package utils.excelutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtil {
	//Main Directory of the project
	public static final String currentDir = System.getProperty("user.dir");

	//Location of Test data excel file
	static URL excelFile = null;

	public static URL getExcelFile()
	{
		return excelFile;
	}

	public static void setExcelFile(URL excelFile)
	{
		ExcelUtil.excelFile = excelFile;
	}

	//Excel WorkBook
	private static XSSFWorkbook excelWBook;

	//Excel Sheet
	private static XSSFSheet excelWSheet;

	//Excel cell
	private static XSSFCell cell;

	//Excel row
	private static XSSFRow row;

	//Row Number
	public static int rowNumber;

	//Column Number
	public static int columnNumber;

	//Setter and Getters of row and columns
	public static void setRowNumber(int pRowNumber) {
		rowNumber = pRowNumber;
	}

	public static int getRowNumber() {
		return rowNumber;
	}

	public static void setColumnNumber(int pColumnNumber) {
		columnNumber = pColumnNumber;
	}

	public static int getColumnNumber() {
		return columnNumber;
	}

	// This method has two parameters: "Test data excel file name" and "Excel sheet name"
	// It creates FileInputStream and set excel file and excel sheet to excelWBook and excelWSheet variables.
	public static void setExcelFileSheet(String sheetName) throws Exception {
		
		try {
			// Open the Excel file
			excelWBook = new XSSFWorkbook(new File(excelFile.toURI()));
			excelWSheet = excelWBook.getSheet(sheetName);
		} catch (Exception e) {
			try {
				throw (e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static Object[][] getTableArray(String fileName, String sheetName) throws Exception
	{

		String[][] tabArray = null;

		try
		{

			URL excelFile = Thread.currentThread().getContextClassLoader()
					.getResource("xls/" + fileName);

			// Access the required test data sheet

			excelWBook = new XSSFWorkbook(new File(excelFile.toURI()));

			excelWSheet = excelWBook.getSheet(sheetName);

			int startRow = 1;

			int startCol = 1;

			int ci, cj;

			int totalRows = excelWSheet.getLastRowNum();
			ci = 0;

			// you can write a function as well to get Column count

			int totalCols = 4;

			tabArray = new String[totalRows][totalCols];

			ci = 0;

			for (int i = startRow; i <= totalRows; i++, ci++)
			{

				cj = 0;

				for (int j = startCol; j <= totalCols; j++, cj++)
				{

					tabArray[ci][cj] = getCellData(i, j);

					System.out.println(tabArray[ci][cj]);

				}

			}

		}

		catch (FileNotFoundException e)
		{

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		catch (IOException e)
		{

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		return (tabArray);

	}


	//This method reads the test data from the Excel cell.
	//We are passing row number and column number as parameters.
	public static String getCellData(int RowNum, int ColNum) {
		try {
			cell = excelWSheet.getRow(RowNum).getCell(ColNum);
			DataFormatter formatter = new DataFormatter();
			String cellData = formatter.formatCellValue(cell);
			return cellData;
		} catch (Exception e) {
			throw (e);
		}
	}

	//This method takes row number as a parameter and returns the data of given row number.
	public static XSSFRow getRowData(int RowNum) {
		try {
			row = excelWSheet.getRow(RowNum);
			return row;
		} catch (Exception e) {
			throw (e);
		}
	}

	//This method gets excel file, row and column number and set a value to the that cell.
	public static void setCellData(String value, int RowNum, int ColNum) throws Exception {
		try {
			row = excelWSheet.getRow(RowNum);
			cell = row.getCell(ColNum);
			if (cell == null) {
				cell = row.createCell(ColNum);
				cell.setCellValue(value);
			} else {
				cell.setCellValue(value);
			}
			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream(new File(excelFile.toURI()));
			excelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			try {
				throw (e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}

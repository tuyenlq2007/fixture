package com.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FixtureGetExcelFileFromSheetByRowByCol {
	private final List<List<List<String>>> getContent;

	public FixtureGetExcelFileFromSheetByRowByCol(String fileName, String sheetName, String rownumber, String colnumber)
			throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		String text1 = "";
		try {
			final FileInputStream excelFile = new FileInputStream(new File(fileName));
			final Workbook workbook = new XSSFWorkbook(excelFile);
			final Sheet datatypeSheet = workbook.getSheet(sheetName);
			final List<List<String>> rentApp = new LinkedList(Arrays.asList());
			
//			final Iterator<Row> iterator = datatypeSheet.iterator();
//			int j = 0;
//			while (iterator.hasNext()) {
//				final Row currentRow = iterator.next();
//				if (j == Integer.parseInt(rownumber)) {
//					final Iterator<Cell> cellIterator = currentRow.iterator();
//					final List<List<String>> rentApp = new LinkedList(Arrays.asList());
//					int i = 0;
//					while (cellIterator.hasNext()) {
//						Integer.toString(++i);
//						final Cell currentCell = cellIterator.next();
//						if (i == Integer.parseInt(colnumber)) {
//							if (currentCell.getCellTypeEnum() == CellType.STRING) {
//								text = currentCell.getStringCellValue();
//							} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
//								text = currentCell.getNumericCellValue() + "";
//							} else {
//								text = "";
//							}
//							rentApp.add(Arrays.asList("Result", text.trim()));
//							getContent.add(rentApp);
//							break;
//						} else {
//							i++;
//						}
//					}
//					break;
//				} else {
//					j++;
//				}
//			}
			Cell cell = datatypeSheet.getRow(Integer.parseInt(rownumber)-1).getCell(Integer.parseInt(colnumber)-1);
			if (cell.getCellTypeEnum() == CellType.STRING) {
				text1 = cell.getStringCellValue();
			} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
				text1 = Integer.toString((int) cell.getNumericCellValue());
			} else if (cell.getCellTypeEnum() == CellType.FORMULA) {
				text1 = Integer.toString((int) cell.getNumericCellValue());
			} else {
				text1 = "";
			}
			rentApp.add(Arrays.asList("Result", text1.trim()));
			getContent.add(rentApp);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		// getContent.add(Arrays.asList(Arrays.asList("Result", text)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}

}
package com.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FixtureGetExcelFileFromSheetByRowByColName {
	private final List<List<List<String>>> getContent;

	public FixtureGetExcelFileFromSheetByRowByColName(String fileName, String sheetName, String rownumber,
			String colName) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		String text = "";
		try {
			final FileInputStream excelFile = new FileInputStream(new File(fileName));
			final Workbook workbook = new XSSFWorkbook(excelFile);
			final Sheet datatypeSheet = workbook.getSheet(sheetName);
			final Iterator<Row> iterator = datatypeSheet.iterator();
			final int colNum = datatypeSheet.getRow(0).getLastCellNum();
			final Map<String, Integer> colMapByName = new HashMap<String, Integer>();
			if (datatypeSheet.getRow(0).cellIterator().hasNext()) {
				for (int j = 0; j < colNum; j++) {
					if (datatypeSheet.getRow(0).getCell(j).getCellTypeEnum() == CellType.STRING) {
						text = datatypeSheet.getRow(0).getCell(j).getStringCellValue();
					} else if (datatypeSheet.getRow(0).getCell(j).getCellTypeEnum() == CellType.NUMERIC) {
						text = datatypeSheet.getRow(0).getCell(j).getNumericCellValue() + "";
					} else {
						text = "";
					}
					colMapByName.put(text, j);
				}
			}
			int j = 0;
			while (iterator.hasNext()) {
				final Row currentRow = iterator.next();
				if (j == Integer.parseInt(rownumber)) {
					currentRow.iterator();
					new LinkedList(Arrays.asList());
					currentRow.getCell(colMapByName.get(colName));
					// int i = 0;
					/*
					 * rentApp.add(Arrays.asList( Arrays.asList(cols.get(0),
					 * "tuyenlq"), Arrays.asList(cols.get(1), "HAWAII")));
					 */
					/*
					 * while (cellIterator.hasNext()) { final String ColName =
					 * "col" + Integer.toString(++i); final Cell currentCell =
					 * cellIterator.next(); //if (i ==
					 * Integer.parseInt(colnumber)) { if (i ==
					 * Integer.parseInt(colnumber)) { if
					 * (currentCell.getCellTypeEnum() == CellType.STRING) { text
					 * = currentCell.getStringCellValue(); } else if
					 * (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
					 * text = currentCell.getNumericCellValue() + ""; } else {
					 * text = ""; } rentApp.add(Arrays.asList("Result",
					 * text.trim())); getContent.add(rentApp); break; } else
					 * i++; }
					 */
					break;
				} else {
					j++;
				}
			}
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
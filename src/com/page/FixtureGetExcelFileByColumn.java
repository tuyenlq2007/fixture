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

public class FixtureGetExcelFileByColumn {
	private final List<List<List<String>>> getContent;

	public FixtureGetExcelFileByColumn(String fileName, String sheet, String colnumber) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		final List<List<String>> rentApp = new LinkedList(Arrays.asList());
		String text = "";
		final String ColName = "col" + colnumber;
		// String FILE_NAME = System.getProperty("user.dir") + File.separator +
		// "FitNesseRoot" + File.separator + "files"
		// + File.separator + fileName;

		try {
			final FileInputStream excelFile = new FileInputStream(new File(fileName));
			final Workbook workbook = new XSSFWorkbook(excelFile);
			final Sheet datatypeSheet = workbook.getSheet(sheet);
			Row row;
			for (int rowIndex = 0; rowIndex <= datatypeSheet.getLastRowNum(); rowIndex++) {
				  row = datatypeSheet.getRow(rowIndex);
				  if (row != null) {
				    Cell cell = row.getCell(Integer.parseInt(colnumber));
				    if (cell != null) {
				    	if (cell.getCellTypeEnum() == CellType.STRING) {
							text = cell.getStringCellValue();
						} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
							text = cell.getNumericCellValue() + "";
						} else {
							text = "";
						}
				    	rentApp.add(Arrays.asList(ColName, text.trim()));
						getContent.add(rentApp);
				    }
				  }
				}
			
			/*final Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				final Row currentRow = iterator.next();
				final Iterator<Cell> cellIterator = currentRow.iterator();
				int i = 0;
				final List<List<String>> rentApp = new LinkedList(Arrays.asList());
				while (cellIterator.hasNext()) {
					final String ColName = "col" + Integer.toString(++i);
					final Cell currentCell = cellIterator.next();
					if (i == Integer.parseInt(colnumber)) {
						if (currentCell.getCellTypeEnum() == CellType.STRING) {
							text = currentCell.getStringCellValue();
						} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							text = currentCell.getNumericCellValue() + "";
						} else {
							text = "";
						}
						rentApp.add(Arrays.asList(ColName, text.trim()));
						getContent.add(rentApp);
					}
				}

			}*/
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
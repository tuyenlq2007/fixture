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

public class FixtureGetTestDataFromExcel {
	private final List<List<List<String>>> getContent;

	public FixtureGetTestDataFromExcel(String fileName) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		String text = "";
		// String FILE_NAME = System.getProperty("user.dir") + File.separator +
		// "FitNesseRoot" + File.separator + "files"
		// + File.separator + fileName;

		try {
			final FileInputStream excelFile = new FileInputStream(new File(fileName));
			final Workbook workbook = new XSSFWorkbook(excelFile);
			final Sheet datatypeSheet = workbook.getSheetAt(0);
			final Iterator<Row> iterator = datatypeSheet.iterator();
			final List<String> sColName = new ArrayList<String>();
			if (iterator.hasNext()) {
				final Row headerRow = iterator.next();
				final Iterator<Cell> cellheaderIterator = headerRow.iterator();
				while (cellheaderIterator.hasNext()) {
					final Cell currentCell = cellheaderIterator.next();
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						text = currentCell.getStringCellValue();
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						text = currentCell.getNumericCellValue() + "";
					} else {
						text = "";
					}
					sColName.add(text);
				}
			}
			while (iterator.hasNext()) {
				final Row currentRow = iterator.next();
				final Iterator<Cell> cellIterator = currentRow.iterator();
				int i = 0;
				final List<List<String>> rentApp = new LinkedList(Arrays.asList());
				while (cellIterator.hasNext()) {
					final String ColName = "! define " + sColName.get(i++) + " {";
					final Cell currentCell = cellIterator.next();
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						text = currentCell.getStringCellValue();
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						text = currentCell.getNumericCellValue() + "";
					} else {
						text = "";
					}
					rentApp.add(Arrays.asList("Result", ColName + text.trim() + "} "));
					getContent.add(rentApp);
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
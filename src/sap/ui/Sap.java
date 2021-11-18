package sap.ui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.exception.StopTest;
import org.sikuli.script.App;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class Sap {

	/*
	 * public static void main(String[] args) throws IOException { //
	 * readPdfFileInDownloadFolder("1", "1", "1"); try {
	 * Float.parseFloat("50000000.00"); System.out.println("ok"); } catch
	 * (NumberFormatException e) {
	 * 
	 * } }
	 */

	public boolean pauseFor(final String miliseconds) throws NumberFormatException, InterruptedException {
		Thread.sleep(Long.parseLong(miliseconds));
		return true;
	}

	public void cleanReport() throws StopTest {
		deleteFilesInDownloadFolder();
	}

	public String getBiReportPageRowCol(String pageNumber, String row, String col) throws IOException {
		return readPdfFileInDownloadFolder(pageNumber, row, col);
	}

	private String readPdfFileInDownloadFolder(String pageNumber, String row, String col) throws IOException {
		File dir = new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator);
		String text = "";
		PdfReader reader;

		if (!dir.isDirectory()) {
			return "";
		}

		File[] listFiles = dir.listFiles();
		for (File file : listFiles) {
			reader = new PdfReader(file.getAbsolutePath());
			text = PdfTextExtractor.getTextFromPage(reader, Integer.parseInt(pageNumber));
			reader.close();
		}
		final String[] texts = text.split("\n");
		ArrayList<String> multiLines = new ArrayList<String>();
		for (final String line : texts) {
			String[] numbersInLine = line.split(" ");
			try {
				Integer.parseInt(numbersInLine[0]);
				multiLines.add(line);
			} catch (NumberFormatException e) {
			}
		}
		List<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
		ArrayList<String> newLines;
		ArrayList<String> e = new ArrayList<String>();
		e.add("");
		lines.add(e);

		for (String oneLine : multiLines) {
			newLines = new ArrayList<String>();
			String[] splitLine = oneLine.split(" ");
			int index = 0;
			String temp = "";
			for (String split : splitLine) {
				if (index < 5) {
					newLines.add(split);
				}

				if (index == 5) {
					temp = split;
				}

				if (index > 5)
					try {
						split = split.replace(",", "");
						Float.parseFloat(split);
						if (temp.equalsIgnoreCase("")) {
							newLines.add(split);
						} else {
							newLines.add(temp);
							newLines.add(split);
						}

						temp = "";
					} catch (NumberFormatException e1) {
						temp = temp + split;
					}

				index++;
			}

			for (String j : newLines) {
				System.out.print(j);
			}
			System.out.println("");
			lines.add(newLines);
		}

		return lines.get(Integer.parseInt(row)).get(Integer.parseInt(col));
	}

	public String getRowNumberOfBiReportPageValue(String pageNumber, String searchValue) throws IOException {
		return searchForRownumber(pageNumber, searchValue);
	}

	private String searchForRownumber(String pageNumber, String searchValue) throws IOException {
		List<ArrayList<String>> readPdf = readPdfFileInDownloadFolder(pageNumber);
		int i = 0;
		boolean bfound = false;
		for (i = 0; i < readPdf.size(); i++) {
			for (String lookup : readPdf.get(i)) {
				if (lookup.equalsIgnoreCase(searchValue)) {
					bfound = true;
					break;
				}

			}
			if (bfound)
				break;
		}
		return String.valueOf(i);
	}

	private List<ArrayList<String>> readPdfFileInDownloadFolder(String pageNumber) throws IOException {
		File dir = new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator);
		String text = "";
		PdfReader reader;

		if (!dir.isDirectory()) {
			return null;
		}

		File[] listFiles = dir.listFiles();
		for (File file : listFiles) {
			reader = new PdfReader(file.getAbsolutePath());
			text = PdfTextExtractor.getTextFromPage(reader, Integer.parseInt(pageNumber));
			reader.close();
		}
		final String[] texts = text.split("\n");
		ArrayList<String> multiLines = new ArrayList<String>();
		for (final String line : texts) {
			String[] numbersInLine = line.split(" ");
			try {
				Integer.parseInt(numbersInLine[0]);
				multiLines.add(line);
			} catch (NumberFormatException e) {
			}
		}
		List<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
		ArrayList<String> newLines;
		ArrayList<String> e = new ArrayList<String>();
		e.add("");
		lines.add(e);

		for (String oneLine : multiLines) {
			newLines = new ArrayList<String>();
			String[] splitLine = oneLine.split(" ");
			int index = 0;
			String temp = "";
			for (String split : splitLine) {
				if (index < 5) {
					newLines.add(split);
				}

				if (index == 5) {
					temp = split;
				}

				if (index > 5)
					try {
						split = split.replace(",", "");
						Float.parseFloat(split);
						if (temp.equalsIgnoreCase("")) {
							newLines.add(split);
						} else {
							newLines.add(temp);
							newLines.add(split);
						}

						temp = "";
					} catch (NumberFormatException e1) {
						temp = temp + split;
					}

				index++;
			}

			for (String j : newLines) {
				System.out.print(j);
			}
			System.out.println("");
			lines.add(newLines);
		}

		return lines;
	}

	private void deleteFilesInDownloadFolder() throws StopTest {
		File dir = new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator);

		if (!dir.isDirectory()) {
			return;
		}

		File[] listFiles = dir.listFiles();
		for (File file : listFiles) {
			file.delete();
		}
	}

	public void press(String key) throws StopTest {
		try {
			Robot robot = new Robot();
			Thread.sleep(1000);

			switch (key.toUpperCase()) {
			case "DOWN":
				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyRelease(KeyEvent.VK_DOWN);
				break;

			case "UP":
				robot.keyPress(KeyEvent.VK_UP);
				robot.keyRelease(KeyEvent.VK_UP);
				break;
			case "TAB":
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				break;

			case "ENTER":
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				break;

			case "SHIFT_TAB":
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				break;

			case "CTRL_A":
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_A);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_A);
				break;

			case "CTRL_S":
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_S);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_S);
				break;

			case "CTRL_C":
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_C);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_C);
				break;

			case "CTRL_V":
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_V);
				break;

			case "BACK_SPACE":
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				break;

			case "F5":
				robot.keyPress(KeyEvent.VK_F5);
				robot.keyRelease(KeyEvent.VK_F5);
				break;

			case "F9":
				robot.keyPress(KeyEvent.VK_F9);
				robot.keyRelease(KeyEvent.VK_F9);
				break;

			case "SHIFT_END":
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_END);
				robot.keyRelease(KeyEvent.VK_END);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				break;

			case "SHIFT_HOME":
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_HOME);
				robot.keyRelease(KeyEvent.VK_HOME);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			throw new StopTest(e);
		}
	}

	public void put(String copyString) throws StopTest {
		try {
			Robot robot = new Robot();
			(Toolkit.getDefaultToolkit().getSystemClipboard()).setContents(new StringSelection(copyString), null);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
		} catch (Exception e) {
			throw new StopTest(e);
		}
	}

	public void type(String copyString) throws StopTest {
		try {
			final Screen screen = new Screen();
			screen.type(copyString);
		} catch (Exception e) {
			throw new StopTest(e);
		}
	}

	public String get() throws StopTest {
		String result;
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			result = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (Exception e) {
			throw new StopTest(e);
		}
		return result;
	}

	public boolean matchWith(String firstValue, String secondValue) throws StopTest {
		if (!firstValue.trim().equalsIgnoreCase(secondValue.trim())) {
			throw new StopTest("not match");
		}
		return firstValue.trim().equalsIgnoreCase(secondValue.trim());
	}

	public void clickOn(final String imageName) throws StopTest {
		try {
			final Screen screen = new Screen();
			final String image = System.getProperty("user.dir") + File.separator + "FitNesseRoot" + File.separator
					+ "files" + File.separator + "images" + File.separator + imageName + ".PNG";
			final Pattern pattern = new Pattern(image).exact();
			screen.click(pattern);
		} catch (Exception e) {
			throw new StopTest(e);
		}
	}

	public void focus(final String appName) throws StopTest {
		try {
			App myApp = new App(appName);
			myApp.focus();
		} catch (Exception e) {
			throw new StopTest(e);
		}
	}

	public String IGetExcelValueAtFileAtSheetAtRowAtColoumn(String fileName, String sheetName, String rownumber,
			String colnumber) throws IOException {
		String text = "";
		final FileInputStream excelFile = new FileInputStream(new File(fileName));
		final Workbook workbook = new XSSFWorkbook(excelFile);
		final Sheet datatypeSheet = workbook.getSheet(sheetName);
		final Iterator<Row> iterator = datatypeSheet.iterator();
		int j = 0;
		while (iterator.hasNext()) {
			final Row currentRow = iterator.next();
			if (j == Integer.parseInt(rownumber)) {
				final Iterator<Cell> cellIterator = currentRow.iterator();
				int i = 0;
				while (cellIterator.hasNext()) {
					Integer.toString(++i);
					final Cell currentCell = cellIterator.next();
					if (i == Integer.parseInt(colnumber)) {
						if (currentCell.getCellTypeEnum() == CellType.STRING) {
							text = currentCell.getStringCellValue();
						} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							text = currentCell.getNumericCellValue() + "";
						} else {
							text = "";
						}
						break;
					} else {
						i++;
					}
				}
				break;
			} else {
				j++;
			}
		}
		return text;
	}

	public String IGetExcelValueAtFileAtSheetAtRowAtColumnName(String fileName, String sheetName, String rownumber,
			String colName) {
		String text = "";
		String text1 = "";
		try {
			final FileInputStream excelFile = new FileInputStream(new File(fileName));
			final Workbook workbook = new XSSFWorkbook(excelFile);
			final Sheet datatypeSheet = workbook.getSheet(sheetName);
			final Iterator<Row> iterator = datatypeSheet.iterator();
			Cell cell = null;
			final int colNum = datatypeSheet.getRow(0).getLastCellNum();
			final Map<String, Integer> colMapByName = new HashMap<String, Integer>();
			if (datatypeSheet.getRow(0).cellIterator().hasNext()) {
				for (int k = 0; k < colNum; k++) {
					if (datatypeSheet.getRow(0).getCell(k).getCellTypeEnum() == CellType.STRING) {
						text = datatypeSheet.getRow(0).getCell(k).getStringCellValue();
					} else if (datatypeSheet.getRow(0).getCell(k).getCellTypeEnum() == CellType.NUMERIC) {
						text = Integer.toString((int) datatypeSheet.getRow(0).getCell(k).getNumericCellValue());
					} else if (datatypeSheet.getRow(0).getCell(k).getCellTypeEnum() == CellType.FORMULA) {
						text = Integer.toString((int) datatypeSheet.getRow(0).getCell(k).getNumericCellValue());
					} else {
						text = "";
					}

					colMapByName.put(text, k);
				}
			}

			int j = -1;

			while (iterator.hasNext()) {
				final Row currentRow = iterator.next();
				j = j + 1;
				if (j == Integer.parseInt(rownumber)) {
					currentRow.iterator();
					cell = currentRow.getCell(colMapByName.get(colName));
					if (cell.getCellTypeEnum() == CellType.STRING) {
						text1 = cell.getStringCellValue();
					} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						text1 = Integer.toString((int) cell.getNumericCellValue());
					} else if (cell.getCellTypeEnum() == CellType.FORMULA) {
						text1 = Integer.toString((int) cell.getNumericCellValue());
					} else {
						text1 = "";
					}
				}
			}
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return text1;
	}

	public String loopScenarioFromRowToRow(final String sScenario, final String fromRow, final String toRow) {
		final List<String> rentApp = new LinkedList(Arrays.asList());
		final int ifromRow = Integer.parseInt(fromRow);
		final int itoRow = Integer.parseInt(toRow);
		for (int i = ifromRow; i <= itoRow; i++) {
			rentApp.add("!define row {" + String.valueOf(i) + "}," + "!include " + sScenario);
		}
		String output = rentApp.toString();
		output = output.replace(",", "\r\n ");
		output = output.replace("[", "").replace("]", "\r\n ");
		return output;
	}

	public void putNumber(String policyNumber) throws AWTException {
		for (int iterator = 0; iterator < policyNumber.length(); iterator++) {
			char eachPolicyNumber = policyNumber.charAt(iterator);
			sendKey(eachPolicyNumber);
		}
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	private void sendKey(char inputKey) throws AWTException {
		Robot robot = new Robot();
		switch (inputKey) {
		case '0':
			robot.keyPress(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_0);
			break;
		case '1':
			robot.keyPress(KeyEvent.VK_1);
			robot.keyRelease(KeyEvent.VK_1);
			break;
		case '2':
			robot.keyPress(KeyEvent.VK_2);
			robot.keyRelease(KeyEvent.VK_2);
			break;
		case '3':
			robot.keyPress(KeyEvent.VK_3);
			robot.keyRelease(KeyEvent.VK_3);
			break;
		case '4':
			robot.keyPress(KeyEvent.VK_4);
			robot.keyRelease(KeyEvent.VK_4);
			break;
		case '5':
			robot.keyPress(KeyEvent.VK_5);
			robot.keyRelease(KeyEvent.VK_5);
			break;
		case '6':
			robot.keyPress(KeyEvent.VK_6);
			robot.keyRelease(KeyEvent.VK_6);
			break;
		case '7':
			robot.keyPress(KeyEvent.VK_7);
			robot.keyRelease(KeyEvent.VK_7);
			break;
		case '8':
			robot.keyPress(KeyEvent.VK_8);
			robot.keyRelease(KeyEvent.VK_8);
			break;
		case '9':
			robot.keyPress(KeyEvent.VK_9);
			robot.keyRelease(KeyEvent.VK_9);
			break;

		default:
			break;
		}
	}

}
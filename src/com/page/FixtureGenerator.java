package com.page;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import org.configuration.SetUp;
import org.exception.StopTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.selenium.session.DriverPool;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class FixtureGenerator<T> {
	// final SinglePage appPage;
	// final Open appPage;
	String var = "symbol";

	public FixtureGenerator() {
		// appPage = new SinglePage();
	}

	private <T> List<List<T>> getCombination(final int currentIndex, final List<TempContainer> containers) {
		if (currentIndex == containers.size()) {
			// Skip the items for the last container
			final List<List<T>> combinations = new ArrayList<List<T>>();
			combinations.add(new ArrayList<T>());
			return combinations;
		}

		final List<List<T>> combinations = new ArrayList<List<T>>();
		final TempContainer<T> container = containers.get(currentIndex);
		final List<T> containerItemList = container.getItems();
		// Get combination from next index
		final List<List<T>> suffixList = getCombination(currentIndex + 1, containers);
		final int size = containerItemList.size();
		for (int ii = 0; ii < size; ii++) {
			final T containerItem = containerItemList.get(ii);
			if (suffixList != null) {
				for (final List<T> suffix : suffixList) {
					final int j = 1;
					final List<T> nextCombination = new ArrayList<T>();
					nextCombination.add((T) (var + j + " {" + containerItem + "}"));
					int k = 1;
					for (final T s : suffix) {
						nextCombination.add((T) s.toString().replace(String.valueOf(k), String.valueOf(k + 1)));
						k = k + 1;
					}
					// nextCombination.addAll(suffix);
					combinations.add(nextCombination);
				}
			}
		}
		return combinations;
	}

	public String VerifyTest(final String fileName) throws InterruptedException, AWTException, FindFailed {
		final String results = "SUCCESS";
		Robot robot = new Robot();
		System.setProperty("webdriver.chrome.driver", "C:\\ITS\\selenium\\chromedriver.exe");
		// WebDriver driver = new ChromeDriver();
		// driver.get("https://magic.dev.r193.dxchub.com/omnichannel-ui-lifesuite/");

		ChromeOptions options = new ChromeOptions();
		options.addExtensions(new File("C:\\ITS\\selenium\\selenium.crx"));

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		ChromeDriver driver = new ChromeDriver(capabilities);
		driver.manage().window().setSize((new Dimension(100, 100)));
		
		
		//System.out.println("Opening extension");
		//driver.get("chrome-extension://mooikfkahbdckldjjndioackbalphokd/index.html");
		driver.get("chrome-extension://leonofcckgmedihikplhnngeppmdncce/index.html");
		driver.navigate().refresh();
		System.out.println("Refresh successfully");
		Thread.sleep(5000);
		App.focus("Selenium");
		Thread.sleep(3000);
		//new Screen().click(new Pattern("C:\\ITS\\Capture.PNG").exact());
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Thread.sleep(1000);
		String text = "C:\\SOMPO\\" + fileName + ".side";
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(5000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_R);
		robot.keyRelease(KeyEvent.VK_R);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		return results;
	}

	
	public String IDeleteFileInFolder(final String fileName, final String folderName) {
		final String results = "SCUCESS";
		// final File folder = new File("C:\\temp");
		final File folder = new File(folderName);
		final File[] files = folder.listFiles((FilenameFilter) (dir, name) -> name.matches(fileName));

		for (final File file : files) {
			if (!file.delete()) {
				System.err.println("Can't remove " + file.getAbsolutePath());
			}
		}
		return results;
	}

	public String IGenerateFrom(final String var, String value) {
		// final String input =
		// "Sing,Viet,Eng;Junior,Regular,Senior;Java,Python,Ruby,C";
		final String input = value.replace("{", "").replace("}", "");
		final String[] input1 = input.split(";");
		this.var = var;

		List<String> strings;
		final List<TempContainer> containers = new ArrayList<TempContainer>();
		for (final String input2 : input1) {
			final TempContainer containeri = new TempContainer();
			strings = new ArrayList<String>(Arrays.asList(input2.split(",")));
			containeri.setItems(strings);
			containers.add(containeri);
		}
		// Get combinations
		final List<List<T>> combinations = getCombination(0, containers);
		String output = combinations.toString();
		output = output.replace(",", "\r\n ");
		output = output.replace("[", "").replace("]", "\r\n ");
		return output;
	}

	public String IGenerateLocatorToFor(final String propertyFile, final String firstName) {
		String output = "";
		List<WebElement> el = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver())
				.findElements(By.xpath("//*[@name]"));
		for (final WebElement e : el) {
			String text = "|fillProperty|!-<propertyFile>.properties-!||<firstName><objectname>||!-name=><objectname>-!|\r\n";
			text = text.replaceAll("<objectname>", e.getAttribute("name"));
			output = output + text;
		}

		el = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).findElements(By.xpath("//*[@id]"));
		for (final WebElement e : el) {
			String text = "|fillProperty|!-<propertyFile>.properties-!||<firstName><objectid>||!-id=><objectid>-!|\r\n";
			text = text.replaceAll("<objectid>", e.getAttribute("id"));
			output = output + text;
		}

		output = output.replaceAll("<propertyFile>", propertyFile);
		output = output.replaceAll("<firstName>", firstName);
		return output;
	}

	public String IGetCurrentDate(final String sDateFormat) {
		final SimpleDateFormat sdfDate = new SimpleDateFormat(sDateFormat);
		final Date now = new Date();
		final String strDate = sdfDate.format(now);
		return strDate;
	}

	public String IGetExcelValueAtFileAtSheetAtRowAtColoumn(String fileName, String sheetName, String rownumber,
			String colnumber) {
		String text1 = "";
		try {
			final FileInputStream excelFile = new FileInputStream(new File(fileName));
			final Workbook workbook = new XSSFWorkbook(excelFile);
			final Sheet datatypeSheet = workbook.getSheet(sheetName);
			
			/*final Iterator<Row> iterator = datatypeSheet.iterator();
			int j = 0;
			while (iterator.hasNext()) {
				final Row currentRow = iterator.next();
				if (j == Integer.parseInt(rownumber)) {
					final Iterator<Cell> cellIterator = currentRow.iterator();
					int i = 0;
					while (cellIterator.hasNext()) {
						i++;
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
						} 
					}
					break;
				} else {
					j++;
				}
			}*/
			//final Row currentRow = iterator.next();
			//j = j + 1;
			//if (j == Integer.parseInt(Integer.parseInt(rownumber))) {
				//currentRow.iterator();
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
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return text1;
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
						text = datatypeSheet.getRow(0).getCell(k).getNumericCellValue() + "";
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
				// if (j == Integer.parseInt(rownumber)) {
				if (j == Integer.parseInt(rownumber)) {
					currentRow.iterator();
					cell = currentRow.getCell(colMapByName.get(colName));
					if (cell.getCellTypeEnum() == CellType.STRING) {
						text1 = cell.getStringCellValue();
					} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						text1 = cell.getNumericCellValue() + "";
					} else {
						text1 = "";
					}
				}
				/*
				 * rentApp.add(Arrays.asList( Arrays.asList(cols.get(0),
				 * "tuyenlq"), Arrays.asList(cols.get(1), "HAWAII")));
				 */
				/*
				 * while (cellIterator.hasNext()) { final String ColName = "col"
				 * + Integer.toString(++i); final Cell currentCell =
				 * cellIterator.next(); if (i == Integer.parseInt(colnumber)) {
				 * if (currentCell.getCellTypeEnum() == CellType.STRING) { text
				 * = currentCell.getStringCellValue(); } else if
				 * (currentCell.getCellTypeEnum() == CellType.NUMERIC) { text =
				 * currentCell.getNumericCellValue() + ""; } else { text = ""; }
				 * break; } else i++; } break;
				 */
				// }else j++;
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

	public String IGetNextDate(final String iExtra, final String sDateFormat) {
		final SimpleDateFormat sdfDate = new SimpleDateFormat(sDateFormat);
		// final Date now = new Date();
		Date dt = new Date();
		final Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, Integer.parseInt(iExtra));
		dt = c.getTime();
		final String strDate = sdfDate.format(dt);
		return strDate;
	}

	public String IGetTestCaseFromExcelSheet(final String fileName, final String isheet) {
		final List<String> rentApp = new LinkedList(Arrays.asList());
		rentApp.add("\n");
		String object = "|fillProperty|!-regex.properties-!||!-object-!||!-xpath=>-!|";
		final String var = "!define var {!-value-!}";
		final String element = "|I click on the|info|";
		final String input = "|I type|${info}|in the|info|";
		final String select = "|I Select The|info|By Text|${info}|";
		try {
			final FileInputStream excelFile = new FileInputStream(new File(fileName));
			final Workbook workbook = new XSSFWorkbook(excelFile);
			final Sheet datatypeSheet = workbook.getSheetAt(Integer.parseInt(isheet) - 1);
			Iterator<Row> iterator = datatypeSheet.iterator();
			rentApp.add("|script      |!-FillSetUpInfo-! |");
			object = object.replaceAll("regex", datatypeSheet.getSheetName());
			while (iterator.hasNext()) {
				final Row currentRow = iterator.next();
				final Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					final Cell currentCell = cellIterator.next();

					if (currentCell.getColumnIndex() == 1) {
						if (currentCell.getCellTypeEnum() == CellType.STRING) {
							rentApp.add(object.replaceAll("object", currentCell.getStringCellValue()));
						} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							rentApp.add(currentCell.getNumericCellValue() + "--");
						}
					}
				}
			}

			// 22
			rentApp.add("\n");
			iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				final Row currentRow = iterator.next();
				final Iterator<Cell> cellIterator = currentRow.iterator();
				Cell previousCell = null;
				while (cellIterator.hasNext()) {
					final Cell currentCell = cellIterator.next();
					if (currentCell.getColumnIndex() == 2) {
						if (currentCell.getCellTypeEnum() == CellType.STRING) {
							String temp = var.replaceAll("value", currentCell.getStringCellValue());
							temp = temp.replaceAll("var", previousCell.getStringCellValue());
							rentApp.add(temp);
						} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							String temp = var.replaceAll("value", String.valueOf(currentCell.getNumericCellValue()));
							temp = temp.replaceAll("var", previousCell.getStringCellValue());
							rentApp.add(temp);
						}
					}

					previousCell = currentCell;
				}
			}

			// 23
			rentApp.add("\n");
			iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {
				final Row currentRow = iterator.next();
				final Iterator<Cell> cellIterator = currentRow.iterator();
				Cell previousCell = null;
				while (cellIterator.hasNext()) {
					final Cell currentCell = cellIterator.next();

					if (currentCell.getColumnIndex() == 1) {
						if (previousCell.getCellTypeEnum() == CellType.STRING) {
							if (previousCell.getStringCellValue().trim().equalsIgnoreCase("element")) {
								rentApp.add("|script|!-FixtureFillPage-!|");
								rentApp.add(element.replaceAll("info", currentCell.getStringCellValue().trim()) + "\n");
							}
							if (previousCell.getStringCellValue().trim().equalsIgnoreCase("input")) {
								rentApp.add("|script|!-FixtureFillPage-!|");
								rentApp.add(input.replaceAll("info", currentCell.getStringCellValue().trim()) + "\n");
							}
							if (previousCell.getStringCellValue().trim().equalsIgnoreCase("select")) {
								rentApp.add("|script|!-FixtureFillPage-!|");
								rentApp.add(select.replaceAll("info", currentCell.getStringCellValue().trim()) + "\n");
							}
						} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							// System.out.print(currentCell.getNumericCellValue()
							// + "--");
						}
					}
					previousCell = currentCell;
				}
			}

		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		String output = rentApp.toString();
		output = output.replace(",", "\r\n ");
		output = output.replace("[", "").replace("]", "\r\n ");
		return output;
	}

	public String IGetTestDataFromExcelSheet(final String fileName, final String isheet) {
		final List<String> rentApp = new LinkedList(Arrays.asList());
		String text = "";
		// String FILE_NAME = System.getProperty("user.dir") + File.separator +
		// "FitNesseRoot" + File.separator + "files"
		// + File.separator + fileName;

		try {
			final FileInputStream excelFile = new FileInputStream(new File(fileName));
			final Workbook workbook = new XSSFWorkbook(excelFile);
			final Sheet datatypeSheet = workbook.getSheetAt(Integer.parseInt(isheet) - 1);
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
				while (cellIterator.hasNext()) {
					final String ColName = "!define " + sColName.get(i++) + " {";
					final Cell currentCell = cellIterator.next();
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						text = currentCell.getStringCellValue();
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						text = currentCell.getNumericCellValue() + "";
					} else {
						text = "";
					}
					rentApp.add(ColName + text.trim() + "} ");
				}

			}
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		String output = rentApp.toString();
		output = output.replace(",", "\r\n ");
		output = output.replace("[", "").replace("]", "\r\n ");
		return output;
	}

	public boolean IPaste(final String sValue) {
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		final StringSelection str = new StringSelection(sValue);
		clipboard.setContents(str, null);

		Robot r;
		try {
			r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_CONTROL);
			r.keyRelease(KeyEvent.VK_V);
		} catch (final AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public String loopScenarioFromRowToRow(final String sScenario, final String fromRow, final String toRow) {
		final List<String> rentApp = new LinkedList(Arrays.asList());
		final int ifromRow = Integer.parseInt(fromRow);
		final int itoRow = Integer.parseInt(toRow);
		for (int i = ifromRow; i <= itoRow; i++) {
			// final List<String> sColName = new ArrayList<String>();
			// sColName.add(sScenario);
			// rentApp.add("!define row {" + new Integer(i) + "}"+ "!include " +
			// sScenario);
			rentApp.add("!define row {" + String.valueOf(i) + "}," + "!include " + sScenario);
		}
		// rentApp.add(sScenario);
		/*
		 * try { final FileInputStream excelFile = new FileInputStream(new
		 * File(fileName)); final Workbook workbook = new
		 * XSSFWorkbook(excelFile); final Sheet datatypeSheet =
		 * workbook.getSheetAt(Integer.parseInt(isheet) - 1); final
		 * Iterator<Row> iterator = datatypeSheet.iterator(); final List<String>
		 * sColName = new ArrayList<String>(); if (iterator.hasNext()) { final
		 * Row headerRow = iterator.next(); final Iterator<Cell>
		 * cellheaderIterator = headerRow.iterator(); while
		 * (cellheaderIterator.hasNext()) { final Cell currentCell =
		 * cellheaderIterator.next(); if (currentCell.getCellTypeEnum() ==
		 * CellType.STRING) { text = currentCell.getStringCellValue(); } else if
		 * (currentCell.getCellTypeEnum() == CellType.NUMERIC) { text =
		 * currentCell.getNumericCellValue() + ""; } else { text = ""; }
		 * sColName.add(text); } } while (iterator.hasNext()) { final Row
		 * currentRow = iterator.next(); final Iterator<Cell> cellIterator =
		 * currentRow.iterator(); int i = 0; while (cellIterator.hasNext()) {
		 * final String ColName = "!define " + sColName.get(i++) + " {"; final
		 * Cell currentCell = cellIterator.next(); if
		 * (currentCell.getCellTypeEnum() == CellType.STRING) { text =
		 * currentCell.getStringCellValue(); } else if
		 * (currentCell.getCellTypeEnum() == CellType.NUMERIC) { text =
		 * currentCell.getNumericCellValue() + ""; } else { text = ""; }
		 * rentApp.add(ColName + text.trim() + "} "); }
		 *
		 * } } catch (final FileNotFoundException e) { e.printStackTrace(); }
		 * catch (final IOException e) { e.printStackTrace(); } catch (final
		 * Exception e) { e.printStackTrace(); }
		 */

		String output = rentApp.toString();
		output = output.replace(",", "\r\n ");
		output = output.replace("[", "").replace("]", "\r\n ");
		return output;
	}
	

	public String calculate(String operand1, String operator, String operand2) {
		switch (operator) {
		case "+":
			return Integer.toString(Integer.parseInt(operand1) + Integer.parseInt(operand2));
		case "-":
			return Integer.toString(Integer.parseInt(operand1) - Integer.parseInt(operand2));
		case "*":
			return Integer.toString(Integer.parseInt(operand1) * Integer.parseInt(operand2));
		case "/":
			return Integer.toString(Integer.parseInt(operand1) / Integer.parseInt(operand2));		
		case "%":
			return Integer.toString(Integer.parseInt(operand1) % Integer.parseInt(operand2));
		default:
			throw new RuntimeException();
		}
	}
	
	public String calculateDouble (String operand1, String operator, String operand2) {
		switch (operator) {
		case "+":
			return Double.toString(Double.parseDouble(operand1) + Double.parseDouble(operand2));
		case "-":
			return Double.toString(Double.parseDouble(operand1) - Double.parseDouble(operand2));
		case "*":
			return Double.toString(Double.parseDouble(operand1) * Double.parseDouble(operand2));
		case "/":
			return Double.toString(Double.parseDouble(operand1) / Double.parseDouble(operand2));		
		case "%":
			return Double.toString(Double.parseDouble(operand1) % Double.parseDouble(operand2));
		default:
			throw new RuntimeException();
		}
	}
	
	public String calculateAge(String birthDate, String format) {
		LocalDate DoB = formatDate(birthDate, format);
		LocalDate.now();
        if ((DoB != null)) {
            return Integer.toString(Period.between(DoB, LocalDate.now()).getYears());
        } else {
            return "0";
        }
    }
	
	private LocalDate formatDate(String dateStr, String patern){
		   //Converting String in format 'dd-MMM-yyyy' to LocalDate

		   DateTimeFormatter formatter_1=DateTimeFormatter.ofPattern(patern);
		   LocalDate localDate_1= LocalDate.parse(dateStr,formatter_1);
		   return localDate_1;
		 }
	
	private boolean cleardata(String dbName) throws IOException, ClassNotFoundException {
		File file = new File(dbName);
		FileInputStream f = new FileInputStream(file);
		ObjectInputStream input = new ObjectInputStream(f);
		// Reads the first object in
		Object readObject = input.readObject();
		input.close();
		f.close();
		HashMap<String, Long> map = new HashMap<String, Long>();
		try {
			map = (HashMap<String, Long>) readObject;
		} catch (Exception e) {
		}
		// Prints out everything in the map.
		map.clear();
		{
			// File file = new File("C:\\ITS\\lmdb.txt");
			FileOutputStream f2 = new FileOutputStream(file);
			ObjectOutputStream s = new ObjectOutputStream(f2);
			s.writeObject(map);
			s.close();
			f2.close();
		}
		return true;
	}

	
	public boolean StartTime(String testcaseId) throws ClassNotFoundException, IOException {
		putMap(testcaseId, System.currentTimeMillis(), "C:/ITS/performance.dat");
		return true;
	}
	
	public boolean IPauseFor(final String miliseconds) throws StopTest, NumberFormatException, InterruptedException {
		Thread.sleep(Long.parseLong(miliseconds));
		return true;

	}
	
	public boolean EndTime(String testcaseId) throws ClassNotFoundException, IOException {
		long end_time = System.currentTimeMillis();
		HashMap<String, Long> map = getMap("C:/ITS/performance.dat");
		
		long difference = end_time - getvalue(map, testcaseId);
		putMap(testcaseId, difference, "C:/ITS/performance.dat");
		return true;
	}
	
	private Long getvalue(HashMap<String, Long> map, String key) {
		Long value = map.get(key);
		return value == null ? 0 : value;
	}
	
	private HashMap<String, Long> getMap(String db) throws IOException, ClassNotFoundException {
		final File file = new File(db);
		FileInputStream f = new FileInputStream(file);
		final ObjectInputStream input = new ObjectInputStream(f);
		// Reads the first object in
		final Object readObject = input.readObject();
		input.close();
		return (HashMap<String, Long>) readObject;
	}
	
	@SuppressWarnings("unchecked")
	private void putMap(String key, long value, String database) throws IOException, ClassNotFoundException {
		final File file = new File(database);
		FileInputStream f = new FileInputStream(file);
		final ObjectInputStream input = new ObjectInputStream(f);
		// Reads the first object in
		final Object readObject = input.readObject();
		input.close();
		f.close();
		HashMap<String, Long> map = new HashMap<String, Long>();
		try {
			map = (HashMap<String, Long>) readObject;
		} catch (Exception e) {
		}
		// Prints out everything in the map.
		map.put(key, value);
		{
			//File file2 = new File(database);
			FileOutputStream f2 = new FileOutputStream(file);
			ObjectOutputStream s = new ObjectOutputStream(f2);
			s.writeObject(map);
			s.close();
			f2.close();
		}
	}

	
	public boolean cleanDatabase() throws IOException, ClassNotFoundException {
		cleardata("C:/ITS/performance.dat");
		return true;
	}

}
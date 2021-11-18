package com.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.configuration.SetUp;
import org.exception.StopTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.page.driver.PageAction;
import org.selenium.session.DriverPool;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class FixtureFillPage {


	public FixtureFillPage() {

	}

	public boolean ICheckOnThe(final String element) throws StopTest {
		new PageAction(element).check();
		return true;
	}

	
	public boolean IClickMouseover(final String element) throws StopTest {
		new PageAction(element).clickMouseover();
		return true;

	}

	public boolean IClickIfExisted(final String element1, final String element2) throws StopTest {
		new PageAction(element1, element2).clickif();
		return true;

	}

	public boolean IClickjsOnThe(final String element) throws StopTest {
		new PageAction(element).clickjs();
		return true;

	}

	public boolean IClickOnAndWaitFor(final String element1, final String element2) throws StopTest {
		new PageAction(element1, element2).clickandwait();
		return true;

	}

	public boolean IClickOnAndWaitForLoading(final String element1, final String element2) throws StopTest {
		new PageAction(element1, element2).clickandwaitloading();
		return true;

	}

	public boolean IClickOnSubMenu(final String sParentX, final String sParentY, final String sChildX,
			final String sChildY) throws StopTest, InterruptedException {
		try {
			final Robot r = new Robot();
			r.mouseMove(Integer.parseInt(sParentX), Integer.parseInt(sParentY));
			Thread.sleep(100);
			r.mousePress(InputEvent.BUTTON1_MASK);
			Thread.sleep(100);
			r.mouseMove(Integer.parseInt(sChildX), Integer.parseInt(sChildY));
			r.mousePress(InputEvent.BUTTON1_MASK);
			Thread.sleep(100);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(100);
			r.mousePress(InputEvent.BUTTON1_MASK);
			Thread.sleep(100);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (final AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	

	public boolean IClose() throws StopTest {
		new PageAction().close();
		return true;

	}

	public boolean IDeClick(final String element) throws StopTest {
		new PageAction(element).declick();
		return true;

	}

	public boolean IEnterInThe(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).enter();
		return true;
	}

	public boolean enterIn(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).enter();
		return true;
	}

	
	public boolean IEnterjsInThe(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).enterjs();
		return true;
	}

	public String IGetByAttribute(final String element, final String attribute) throws StopTest {
		return new PageAction(element, attribute).getAttribute();

	}

	public String captureEvidence() throws StopTest {
		return new PageAction().getEvidence();

	}
	
	public String IGetByValue(final String element) throws StopTest {
		return new PageAction(element).getValue();

	}

	public String IGetDigitOf(final String value) throws StopTest {
		return new PageAction(value).getDigit();
	}

	public String IGetOutputOf(final String javascript) throws StopTest {
		return new PageAction(javascript).getJs();
	}

	public String IGetRandom(final String value) throws StopTest {
		return new PageAction(value).getRandom();
	}

	public String IGetRandomNumber(final String value) throws StopTest {
		return new PageAction(value).getRandomNumber();
	}

	public String IGetTextFromFile(final String fileName) throws StopTest {
		String text = "";
		final String filepath = System.getProperty("user.dir") + File.separator + "FitNesseRoot" + File.separator
				+ "files" + File.separator + fileName;

		BufferedReader br = null;
		FileReader fr = null;

		try {

			// br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(filepath);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				text = sCurrentLine;
			}

		} catch (final IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null) {
					br.close();
				}

				if (fr != null) {
					fr.close();
				}

			} catch (final IOException ex) {

				ex.printStackTrace();

			}

		}
		return text;
	}

	public boolean IGoBack() throws StopTest {
		new PageAction().goback();
		return true;

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
		case 'a':
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			break;
		case 'b':
			robot.keyPress(KeyEvent.VK_B);
			robot.keyRelease(KeyEvent.VK_B);
			break;

		case 'c':
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
			break;

		case 'd':
			robot.keyPress(KeyEvent.VK_D);
			robot.keyRelease(KeyEvent.VK_D);
			break;

		case 'e':
			robot.keyPress(KeyEvent.VK_E);
			robot.keyRelease(KeyEvent.VK_E);
			break;

		case 'f':
			robot.keyPress(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_F);
			break;

		case 'g':
			robot.keyPress(KeyEvent.VK_G);
			robot.keyRelease(KeyEvent.VK_G);
			break;

		case 'h':
			robot.keyPress(KeyEvent.VK_H);
			robot.keyRelease(KeyEvent.VK_H);
			break;

		case 'i':
			robot.keyPress(KeyEvent.VK_I);
			robot.keyRelease(KeyEvent.VK_I);
			break;

		case 'j':
			robot.keyPress(KeyEvent.VK_J);
			robot.keyRelease(KeyEvent.VK_J);
			break;

		case 'k':
			robot.keyPress(KeyEvent.VK_K);
			robot.keyRelease(KeyEvent.VK_K);
			break;

		case 'l':
			robot.keyPress(KeyEvent.VK_L);
			robot.keyRelease(KeyEvent.VK_L);
			break;

		case 'm':
			robot.keyPress(KeyEvent.VK_M);
			robot.keyRelease(KeyEvent.VK_M);
			break;

		case 'n':
			robot.keyPress(KeyEvent.VK_N);
			robot.keyRelease(KeyEvent.VK_N);
			break;

		case 'o':
			robot.keyPress(KeyEvent.VK_O);
			robot.keyRelease(KeyEvent.VK_O);
			break;

		case 'p':
			robot.keyPress(KeyEvent.VK_P);
			robot.keyRelease(KeyEvent.VK_P);
			break;

		case 'q':
			robot.keyPress(KeyEvent.VK_Q);
			robot.keyRelease(KeyEvent.VK_Q);
			break;

		case 'r':
			robot.keyPress(KeyEvent.VK_R);
			robot.keyRelease(KeyEvent.VK_R);
			break;

		case 's':
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_S);
			break;

		case 't':
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_T);
			break;

		case 'u':
			robot.keyPress(KeyEvent.VK_U);
			robot.keyRelease(KeyEvent.VK_U);
			break;

		case 'v':
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			break;

		case 'w':
			robot.keyPress(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_W);
			break;

		case 'x':
			robot.keyPress(KeyEvent.VK_X);
			robot.keyRelease(KeyEvent.VK_X);
			break;

		case 'y':
			robot.keyPress(KeyEvent.VK_Y);
			robot.keyRelease(KeyEvent.VK_Y);
			break;

		case 'z':
			robot.keyPress(KeyEvent.VK_Z);
			robot.keyRelease(KeyEvent.VK_Z);
			break;

		default:
			break;
		}
	}

	
	public boolean IInputjsInto(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).typeJs();
		return true;
	}

	public boolean IOpen(final String sUrl) throws StopTest {
		final PageAction pageAction = new PageAction(sUrl);
		pageAction.open();
		return true;

	}

	public boolean open(final String sUrl) throws StopTest {
		final PageAction pageAction = new PageAction(sUrl);
		pageAction.open();
		return true;

	}

	public boolean IOpenWindow(final String sUrl) throws StopTest {
		final PageAction pageAction = new PageAction(sUrl);
		pageAction.openWindow();
		return true;

	}

	public boolean IPauseFor(final String miliseconds) throws StopTest {
		new PageAction(miliseconds).pause();
		return true;

	}

	public boolean pause(final String miliseconds) throws StopTest {
		new PageAction(miliseconds).pause();
		return true;

	}

	public boolean IRunTheScript(final String script) throws StopTest {
		new PageAction(script).runscript();
		return true;

	}
	public boolean executeJavascript(final String script) throws StopTest {
		new PageAction(script).runscript();
		return true;

	}

	
	public boolean IScrollTo(final String element) throws StopTest {
		new PageAction(element).scrolljs();
		return true;

	}

	public boolean ISelectjsByValue(final String element, final String value) throws StopTest {
		new PageAction(element, value).selectjsByValue();
		return true;

	}

	public boolean ISelectjsTheByIndex(final String element, final String value) throws StopTest {
		new PageAction(element, value).selectjsByIndex();
		return true;

	}

	public boolean ISelectTheByIndex(final String element, final String value) throws StopTest {
		new PageAction(element, value).selectByIndex();
		return true;

	}

	public boolean ISelectTheByText(final String element, final String value) throws StopTest {
		new PageAction(element, value).selectByText();
		return true;

	}

	public boolean ISelectByText(final String element, final String value) throws StopTest {
		new PageAction(element, value).chooseByText();
		return true;

	}

	public boolean ISelectTheByValue(final String element, final String value) throws StopTest {
		new PageAction(element, value).selectByValue();
		return true;

	}

	public boolean ISetjsTextInThe(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).setjstext();
		return true;
	}

	public boolean ISubmitKeyDown(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).keydown();
		return true;
	}

	public boolean ISubmitKeyPress(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).keypress();
		return true;
	}

	public boolean ISubmitKeyUp(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).keyup();
		return true;
	}

	public boolean ISubmitMouse(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).mouseEventHandler();
		return true;
	}

	public boolean ISubmitThe(final String element) throws StopTest {
		new PageAction(element).submit();
		return true;

	}

	public boolean ISwitchToFrame(final String framename) throws StopTest {
		new PageAction(framename).switchToFrame();
		return true;

	}

	public boolean ISwitchToParentFrame() throws StopTest {
		new PageAction().switchToParentFrame();
		return true;

	}

	public boolean ISwitchToWindow(final String element) throws StopTest {
		new PageAction(element).switchToWindow();
		return true;

	}

	public boolean ITypeInThe(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).type();
		return true;
	}

	public boolean typeIn(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).type();
		return true;
	}
	
	public boolean IUncheckOnThe(final String element) throws StopTest {
		new PageAction(element).uncheck();
		return true;
	}

	public boolean IWaitForCondition(final String timeout, final String sCondition) throws StopTest {
		new PageAction(timeout, sCondition).waitforCondition();
		return true;

	}

	public boolean IWaitForElementIsEnabled(final String timeout, final String element) throws StopTest {
		new PageAction(timeout, element).waitForElementIsEnabled();
		return true;

	}
	
	public boolean IWaitForElementIn(final String element, final String timeout) throws StopTest {
		new PageAction(timeout, element).waitForElement();
		return true;

	}
	
	public boolean IWaitForIn(final String element, final String timeout) throws StopTest {
		new PageAction(element, timeout).waitForElement();
		return true;

	}
	
	public boolean IWaitForPageLoad(final String timeout) throws StopTest {
		new PageAction(timeout).waitForPageLoad();
		return true;

	}

	public String GetElementsByTag(final String tag) throws StopTest {
		List<WebElement> list = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver())
				.findElements(By.tagName(tag));
		String outer = "";
		for (int i = 0; i < list.size(); i++) {
			outer = outer + "index: " + i + " -- html: " + list.get(i).getAttribute("outerHTML") + "\n";
		}
		return outer;

	}

	public boolean FillByTagByIndex(final String enterValue, final String tag, final String index) throws StopTest {
		List<WebElement> list = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver())
				.findElements(By.tagName(tag));
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		final StringSelection str = new StringSelection(enterValue);
		clipboard.setContents(str, null);
		list.get(Integer.parseInt(index)).sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
		return true;

	}

	public boolean FillJsByTagByIndex(final String enterValue, final String tag, final String index) throws StopTest {
		List<WebElement> list = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver())
				.findElements(By.tagName(tag));
		final JavascriptExecutor executor = (JavascriptExecutor) DriverPool.INSTANCE
				.getDriver(SetUp.getSetup().getDriver());
		executor.executeScript("arguments[0].value ='" + enterValue + "'", list.get(Integer.parseInt(index)));
		return true;

	}

	public String takeScreen() {
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss'.png'").format(new Date());
		String filepath = System.getProperty("user.dir") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + "history" + File.separator + fileName;
		File src = ((TakesScreenshot) DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()))
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(filepath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "files/history/" + fileName;

	}

	public void press(String key) throws AWTException, InterruptedException {
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

		case "CTRL_C":
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			break;

		case "CTRL_V":
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
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
	}

	public boolean matchWith(String firstValue, String secondValue) throws StopTest {
		if (!firstValue.trim().equalsIgnoreCase(secondValue.trim())) {
			throw new StopTest("not match");
		}
		return firstValue.trim().equalsIgnoreCase(secondValue.trim());
	}

	public void put(String copyString) throws AWTException {
		Robot robot = new Robot();
		(Toolkit.getDefaultToolkit().getSystemClipboard()).setContents(new StringSelection(copyString), null);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
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
	

	
	public boolean IInputInto(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).typeSlider();
		return true;
	}


	public boolean inputInto(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).typeSlider();
		return true;
	}

	public boolean IInputIn(final String sValue, final String locator) throws StopTest, AWTException {
		new PageAction(sValue, locator).inputIn();
		return true;
	}

	public boolean inputIn(final String sValue, final String locator) throws StopTest, AWTException {
		new PageAction(sValue, locator).inputIn();
		return true;
	}	
	public boolean clickAt(final String element) throws StopTest {
		new PageAction(element).clickAt();
		return true;

	}
	public boolean IClickAt(final String element) throws StopTest {
		new PageAction(element).clickAt();
		return true;

	}

	public boolean ITryToClickUntilElementAppearsWithTimeoutPollingEvery(final String element1, final String element2, final String timeout, final String polling) throws StopTest {
		new PageAction(element1, element2, timeout, polling).tryToClickUntilElementIsEnabledWithTimeoutPollingEvery();
		return true;

	}

	
	public boolean IClickAtWithTimeoutPollingEvery(final String element, final String timeout, final String polling) throws StopTest {
		new PageAction(element, timeout, polling).clickAtWithTimeoutPollingEvery();
		return true;

	}
	
	public boolean IClickOnThe(final String element) throws StopTest {
		new PageAction(element).click();
		return true;

	}
	
	public boolean IClick(final String element) throws StopTest {
		new PageAction(element).click();
		return true;
	}

	public boolean click(final String element) throws StopTest {
		new PageAction(element).click();
		return true;
	}
	
	public boolean WaitForAsync(final String timeout, final String sObject) throws StopTest {
		new PageAction(timeout, sObject).waitforAsync();
		return true;

	}
	
	public boolean IInputInAndWaitFor(final String sValue, final String locator, final String waitLocator) throws StopTest, AWTException {
		new PageAction(sValue, locator, waitLocator).inputInAndWaitFor();
		return true;
	}
	
	public boolean IClickAndWaitFor(final String locator, final String waitLocator) throws StopTest, AWTException {
		new PageAction(locator, waitLocator).clickAndWaitFor();
		return true;
	}
	
	//Android
	public boolean ISendKeyInThe(final String sValue, final String locator) throws StopTest {
		new PageAction(sValue, locator).sendkey();
		return true;
	}
	
	public boolean IPressKeyCode(final int sValue) throws StopTest {
		new PageAction(sValue).presskeycode();
		return true;
	}
	
	public String GetIdOfAndWriteToFile(final String element, final String excelFilePath) throws StopTest {
		String id = new PageAction(element).getid();
		writetofile(element, id, excelFilePath);
		return id;
	}
	//--End Android

	private void writetofile(String key, String value, String excelFilePath) {
        
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);
            int columnCount = 0;        
            Cell cell1 = row.createCell(columnCount);
            cell1.setCellValue((String) key);
            Cell cell2 = row.createCell(++columnCount);
            cell2.setCellValue((String) value);
            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
             
        } catch (IOException | EncryptedDocumentException
                | InvalidFormatException ex) {
            ex.printStackTrace();
        }
    }
		
	/**
	 * 
	 * @param dirPath
	 * @throws IOException
	 * 		Delete all file on current Directory
	 */
	public void DeleteAllFileInCurrentDirectory(String dirPath) throws IOException {
		File[] children = new File(dirPath).listFiles();
		if (children != null) {
			for (File child : children) {
				System.out.println("Delete: " + child.getName());
				deleteFile(child);							
			}
		}		
	}
	/**
	 * 
	 * @param dirPathSource
	 * @param dirPathTarget
	 * @param folderName
	 * @throws IOException
	 * 		Move file from Directory to Directory
	 */
	public void MoveFileFromDirToDir(String dirPathSource, String dirPathTarget, String folderName) throws IOException {		
		// create Dir folderName at dirPathTarget
		String pathNewFolderName = dirPathTarget + "\\" + folderName;
		System.out.println("Create Dir: " + pathNewFolderName);
		makeDir(pathNewFolderName);
		File[] children = new File(dirPathSource).listFiles();		
		Path targetPath = Paths.get(pathNewFolderName);
		if (children != null) {
			for (File child : children) {
				System.out.println("Move file from: " + child.toPath().toString() + "to: " + targetPath.toString());
				Files.move(child.toPath(), targetPath.resolve(child.getName()), StandardCopyOption.ATOMIC_MOVE);						
			}
		}			
	}
	
	/**
	 * 
	 * @param file
	 * @throws IOException
	 * 		Delete current file
	 */
	public static void deleteFile(File file) throws IOException {
		if (!file.exists())
			return;
		if(!file.isFile())
			return;
		if (!file.delete())
			throw new IOException("Could not delete '" + file.getAbsolutePath() + "'");
	}
	/**
	 * 
	 * @param path
	 * @return
	 * 	Make Directory
	 */
	public static boolean makeDir(String path) {
		return new File(path).mkdir();
	}	
}
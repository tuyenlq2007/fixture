package performance.ui;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.configuration.SetUp;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.selenium.session.DriverPool;

import com.api.RestRequests;

import javafx.concurrent.Task;

public class Web {
	

	public boolean testScriptThreadOpen(final String testPage, final String threadId, final String pUrl) throws org.exception.StopTest, ClassNotFoundException, SQLException {
		String url = pUrl;
		long startTime = System.currentTimeMillis();
		if (!url.contains("://")) {
			String baseURL = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).getCurrentUrl();
			if (!baseURL.isEmpty() && baseURL.charAt(baseURL.length() - 1) != '/')
				baseURL += "/";
			try {
				url = new URI(baseURL).resolve(url).toASCIIString();
			} catch (URISyntaxException e) {
				takeScreen();
				try {
					throw new org.exception.StopTest(
							takeScreen() + " Invalid URL: baseURL=[" + baseURL + "] / parameter=[" + url + "]");
				} catch (Exception e1) {
					throw new org.exception.StopTest(
							"This web server is not currently enabled for processing, see the screenshot: "
									+ takeScreen(),
							e1);
				}
			}
		}
		DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).navigate().to(url);
		long endTime = System.currentTimeMillis();
		new LogClass().trace("Test script: " + testPage +" thread: " + threadId + "execution time: " + (endTime-startTime) + "ms");
		return true;

	}
	
	public boolean open(final String pUrl) throws org.exception.StopTest, ClassNotFoundException, SQLException {
		String url = pUrl;
		if (!url.contains("://")) {
			String baseURL = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).getCurrentUrl();
			if (!baseURL.isEmpty() && baseURL.charAt(baseURL.length() - 1) != '/')
				baseURL += "/";
			try {
				url = new URI(baseURL).resolve(url).toASCIIString();
			} catch (URISyntaxException e) {
				takeScreen();
				try {
					throw new org.exception.StopTest(
							takeScreen() + " Invalid URL: baseURL=[" + baseURL + "] / parameter=[" + url + "]");
				} catch (Exception e1) {
					throw new org.exception.StopTest(
							"This web server is not currently enabled for processing, see the screenshot: "
									+ takeScreen(),
							e1);
				}
			}
		}
		DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).navigate().to(url);
		return true;

	}

	public boolean click(final String locator) throws org.exception.StopTest {

		try {
			((JavascriptExecutor) DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()))
					.executeScript("arguments[0].click();", new WebApi(locator).validateElement());
		} catch (final Exception e) {
			try {
				final WebElement element = new WebApi(locator).validateElement();
				final JavascriptExecutor executor = (JavascriptExecutor) DriverPool.INSTANCE
						.getDriver(SetUp.getSetup().getDriver());
				executor.executeScript("arguments[0].scrollIntoView();", element);
				executor.executeScript("arguments[0].click();", element);
			} catch (final Exception e3) {
				throw new org.exception.StopTest(takeScreen(), e3);
			}
		}
		return true;
	}

	public boolean typeIn(final String input, final String locator) throws org.exception.StopTest {

		try {
			final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			final StringSelection str = new StringSelection(input);
			clipboard.setContents(str, null);
			new WebApi(locator).validateElement().sendKeys(Keys.chord(Keys.CONTROL, "v"), "");

		} catch (final Exception e) {
			try {
				final WebElement element = new WebApi(locator).validateElement();
				final JavascriptExecutor executor = (JavascriptExecutor) DriverPool.INSTANCE
						.getDriver(SetUp.getSetup().getDriver());
				executor.executeScript("arguments[0].scrollIntoView();", element);
				final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				final StringSelection str = new StringSelection(input);
				clipboard.setContents(str, null);
				element.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
			} catch (final Exception e3) {
				throw new org.exception.StopTest(takeScreen(), e3);
			}
		}
		return true;
	}

	public boolean chooseAndSelect(final String locator, final String input) throws org.exception.StopTest {

		try {
			final Select select = new Select(new WebApi(locator).validateElement());
			if (select.isMultiple()) {
				select.deselectAll();
			}
			select.selectByVisibleText(input);
		} catch (final Exception e) {
			try {
				final Select select = new Select(new WebApi(locator).validateElement());
				if (select.isMultiple()) {
					select.deselectAll();
				}
				select.selectByValue(input);
			} catch (final Exception e3) {
				throw new org.exception.StopTest(takeScreen(), e3);
			}
		}
		return true;
	}

	public boolean userStepStart(final String userid, final String stepid, final String stepDescription)
			throws StopTest, ClassNotFoundException, SQLException {
		new Dao().insertDurationTable(userid, stepid, stepDescription, "");
		return true;
	}

	public boolean userStepEnd(final String userid, final String stepid)
			throws StopTest, ClassNotFoundException, SQLException {
		new Dao().insertDurationTable(userid, stepid, "", "");
		return true;
	}

	public boolean executeClient(final String clientUrl) throws Exception {
		new RestRequests().execute(clientUrl);
		return true;
	}

	public boolean deletePerformanceData() throws StopTest, ClassNotFoundException, SQLException {
		new Dao().deleteDurationTable();
		return true;
	}

	public boolean userStepClick(final String userid, final String stepid, final String locator)
			throws StopTest, ClassNotFoundException, SQLException {
		try {
			((JavascriptExecutor) DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()))
					.executeScript("arguments[0].click();", new WebApi(locator).validateElement());
		} catch (final Exception e) {
			throw new StopTest(userid, stepid,
					"This object is not currently enabled for processing, see the screenshot: " + takeScreen());
		}
		return true;
	}

	public boolean userStepTypeIn(final String userid, final String stepid, final String input, final String locator)
			throws StopTest, ClassNotFoundException, SQLException {
		try {
			((JavascriptExecutor) DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()))
					.executeScript("arguments[0].value ='" + input + "'", new WebApi(locator).validateElement());
		} catch (final Exception e) {
			throw new StopTest(userid, stepid,
					"This object is not currently enabled for processing, see the screenshot: " + takeScreen());
		}
		return true;
	}

	public boolean userStepChooseAndSelect(final String userid, final String stepid, final String locator,
			final String input) throws StopTest, ClassNotFoundException, SQLException {
		try {
			final Select select = new Select(new WebApi(locator).validateElement());
			if (select.isMultiple()) {
				select.deselectAll();
			}
			select.selectByVisibleText(input);
		} catch (final Exception e) {
			throw new StopTest(userid, stepid,
					"This object is not currently enabled for processing, see the screenshot: " + takeScreen());
		}
		return true;
	}

	public boolean userStepClose(final String userid, final String stepid)
			throws StopTest, ClassNotFoundException, SQLException {
		try {
			DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).quit();
		} catch (final Exception e) {
			throw new StopTest(userid, stepid,
					"This object is not currently enabled for processing, see the screenshot: " + takeScreen());
		}
		return true;
	}

	public boolean pauseFor(final String miliseconds) throws NumberFormatException, InterruptedException {
		Thread.sleep(Long.parseLong(miliseconds));
		return true;
	}

	public boolean userStepOpen(final String userid, final String stepid, final String pUrl)
			throws StopTest, ClassNotFoundException, SQLException {
		String url = pUrl;
		if (!url.contains("://")) {
			String baseURL = DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).getCurrentUrl();
			if (!baseURL.isEmpty() && baseURL.charAt(baseURL.length() - 1) != '/')
				baseURL += "/";
			try {
				url = new URI(baseURL).resolve(url).toASCIIString();
			} catch (URISyntaxException e) {
				takeScreen();
				try {
					throw new StopTest(
							takeScreen() + " Invalid URL: baseURL=[" + baseURL + "] / parameter=[" + url + "]");
				} catch (Exception e1) {
					throw new StopTest(userid, stepid,
							"This web server is not currently enabled for processing, see the screenshot: "
									+ takeScreen(),
							e1);
				}
			}
		}
		DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).navigate().to(url);
		return true;

	}

	public String userStepGetAttributeOf(final String userid, final String stepid, final String attribute,
			final String locator) throws StopTest, ClassNotFoundException, SQLException {
		String outputAttribute = "";
		try {
			outputAttribute = (String) ((JavascriptExecutor) DriverPool.INSTANCE
					.getDriver(SetUp.getSetup().getDriver())).executeScript(
							"return arguments[0].getAttribute('" + attribute + "');",
							new WebApi(locator).validateElement());

		} catch (final Exception e) {
			throw new StopTest(userid, stepid,
					"This object is not currently enabled for processing, see the screenshot: " + takeScreen());
		}
		return outputAttribute;

	}

	public String userStepGetValueOf(final String userid, final String stepid, final String locator)
			throws StopTest, ClassNotFoundException, SQLException {
		String outputValue = "";
		try {
			outputValue = (String) ((JavascriptExecutor) DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()))
					.executeScript("return arguments[0].value;", new WebApi(locator).validateElement());

		} catch (final Exception e) {
			throw new StopTest(userid, stepid,
					"This object is not currently enabled for processing, see the screenshot: " + takeScreen());
		}
		return outputValue;
	}

	public String userStepGetOutputOfScript(final String userid, final String stepid, final String javaScript)
			throws StopTest {
		String outputValue = "";
		try {
			outputValue = (String) ((JavascriptExecutor) DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()))
					.executeScript(javaScript);
		} catch (final Exception e) {
			throw new StopTest(takeScreen(), e);
		}
		return outputValue;
	}

	public boolean userStepScrollTo(final String userid, final String stepid, final String locator) throws StopTest {
		try {
			((JavascriptExecutor) DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()))
					.executeScript("arguments[0].scrollIntoView();", new WebApi(locator).validateElement());
		} catch (final Exception e) {
			throw new StopTest(userid, stepid,
					"This object is not currently enabled for processing, see the screenshot: " + takeScreen());
		} finally {
			return true;
		}
	}

	public boolean executeTestPort(final String testCase, final String port)
			throws NumberFormatException, InterruptedException, IOException {
		Runtime.getRuntime()
				.exec("cmd /c start cmd.exe /K \"cd C:/ITS/ & java -jar its.jar -p " + port + " -c " + testCase + "\"");
		Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		return true;
	}

	public boolean executeTestGroupThreads(final String testCase, final String numThreads)
			throws NumberFormatException, InterruptedException, IOException {
		int numOfthreads = Integer.parseInt(numThreads);
		GroupThreadFactory factory = new GroupThreadFactory("GroupThreadFactory");
		for (int ithread = 0; ithread < numOfthreads;ithread++ ) {	
			    Thread t = factory.newThread(new Tasks(testCase+"?test&thread=" + ithread));
			    t.start(); 
			}
		/*	
		int numOfthreads = Integer.parseInt(numThreads);	
		for (int ithread = 0; ithread < numOfthreads;ithread++ ) {	
			executeTest(testCase+"?test&thread=" + ithread);
		}
		*/
		return true;
	}
	
	public boolean executeTestWithThreads(final String testCase, final String numThreads)
			throws NumberFormatException, InterruptedException, IOException {
		int numOfthreads = Integer.parseInt(numThreads);
		int startport = 8000;
		/*
		  Runtime.getRuntime().
		  exec("cmd /c start cmd.exe /K \"cd C:/ITS/ & java -jar its.jar -p " + 8101 +
		  " -c " + testCase + "?purgeHistory&days=0\"");
		  Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		 */

		for (int ithread = 0; ithread < numOfthreads;ithread++ ) {
		int runport = startport + ithread;
		Runtime.getRuntime()
				.exec("cmd /c start cmd.exe /K \"cd C:/ITS/ & java -jar its.jar -p " + runport + " -c " + testCase + "?test&thread=" + ithread +"\"");
		Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		Thread.sleep(1000);
		}
		return true;
	}
	
	public boolean executeTest(final String url) throws NumberFormatException, InterruptedException, IOException {
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		try {

			con.setRequestMethod("GET");

			StringBuilder content;

			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

				String line;
				content = new StringBuilder();

				while ((line = in.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
			}

			System.out.println(content.toString());

		} finally {

			con.disconnect();
		}
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
			e.printStackTrace();
		}
		return "files" + File.separator + "history" + File.separator + fileName;
	}
}
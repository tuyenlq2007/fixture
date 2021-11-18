package org.page.direct.driver;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TypeSlider extends WebApi {
	private final String enterValue;

	public TypeSlider(String enterValue, String object_repository) {
		super(object_repository);
		this.enterValue = enterValue;
	}

	public ExpectedCondition<Boolean> angularHasFinishedProcessing() {
		return driver -> {
			final String hasAngularFinishedScript = "try {var callback = arguments[arguments.length - 1];\n"
					+ "var el = document.querySelector('html');\n" + "if (!window.angular) {\n"
					+ "    callback('false')\n" + "}\n" + "if (angular.getTestability) {\n"
					+ "    angular.getTestability(el).whenStable(function(){callback('true')});\n" + "} else {\n"
					+ "    if (!angular.element(el).injector()) {\n" + "        callback('false')\n" + "    }\n"
					+ "    var browser = angular.element(el).injector().get('$browser');\n"
					+ "    browser.notifyWhenNoOutstandingRequests(function(){callback('true')});\n"
					+ "} }catch (e){'true'};";

			final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
			final String isProcessingFinished = javascriptExecutor.executeAsyncScript(hasAngularFinishedScript)
					.toString();

			return Boolean.valueOf(isProcessingFinished);
		};
	}

	@Override
	public String execute() throws StopTest {
		/*
		String result = "SUCCESS";
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		final StringSelection str = new StringSelection(enterValue);
		clipboard.setContents(str, null);
		*/
		
		String result = "SUCCESS";
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(enterValue), null);
		
		try {
			if (!enterValue.equalsIgnoreCase((String) clipboard.getData(DataFlavor.stringFlavor))){
				clipboard.setContents(new StringSelection(enterValue), null);
			}
		} catch (UnsupportedFlavorException | IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}

		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		final String hasAngularFinishedScript = "try {var callback=arguments[arguments.length-1];"
				+ " if (!window.angular) { throw new Error('angular could not be found on the window');}if (angular.getTestability) {angular.getTestability(angular.element(document.body)).whenStable(callback); } else { if (!angular.element(angular.element(document.body)).injector()) { throw new Error('root element (' + 'body' + ') has no injector.' +' this may mean it is not inside ng-app.'); }}"
				+ "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);}catch(err) {}";
		try {
			driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
			executor.executeAsyncScript(hasAngularFinishedScript);
			WebDriverWait wait = new WebDriverWait(driver, 30, 2000);
			wait.until(angularHasFinishedProcessing());
			final WebElement element = validateElement();
			element.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
			String result1 = (String) executor.executeScript("return arguments[0].value;", element);
			if (!result1.toUpperCase().equals(enterValue.toUpperCase())) {
				Thread.sleep(3000);			
				wait.until(angularHasFinishedProcessing());
				element.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
			}
		}catch (Exception e10){
			try {
				driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
				executor.executeAsyncScript(hasAngularFinishedScript);
				WebDriverWait wait = new WebDriverWait(driver, 30, 2000);
				wait.until(angularHasFinishedProcessing());
				final WebElement element0 = validateElement();
				element0.clear();
				element0.sendKeys(enterValue);
				result = (String) executor.executeScript("return arguments[0].value;", element0);
				if (!result.toUpperCase().equals(enterValue.toUpperCase())) {
					Thread.sleep(3000);			
					wait.until(angularHasFinishedProcessing());
					element0.clear();
					element0.sendKeys(enterValue);
				}
				driver.manage().timeouts().setScriptTimeout(0, TimeUnit.SECONDS);
			} catch (final Exception e) {
				try {
					final WebElement element1 = validateElement();
					element1.click();
					element1.clear();
					element1.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
					result = (String) executor.executeScript("return arguments[0].value;", element1);
					if (!result.toUpperCase().equals(enterValue.toUpperCase())) {
						Thread.sleep(3000);
						driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
						executor.executeAsyncScript(hasAngularFinishedScript);
						WebDriverWait wait = new WebDriverWait(driver, 30, 2000);
						wait.until(angularHasFinishedProcessing());
						element1.clear();
						element1.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
					}

				} catch (final Exception e1) {
					try {
						final WebElement element2 = validateElement();
						element2.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
					} catch (final Exception e2) {
						try {
							final JavascriptExecutor executor3 = (JavascriptExecutor) driver;
							final WebElement element3 = validateElement();
							executor3.executeScript("arguments[0].value ='" + enterValue + "'", element3);
							result = (String) executor3.executeScript("return arguments[0].value;", element3);
							if (!result.toUpperCase().equals(enterValue.toUpperCase())) {
								Thread.sleep(3000);
								driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
								executor3.executeAsyncScript(hasAngularFinishedScript);
								WebDriverWait wait3 = new WebDriverWait(driver, 30, 2000);
								wait3.until(angularHasFinishedProcessing());
								executor3.executeScript("arguments[0].value ='" + enterValue + "'", element3);
							}
						} catch (final Exception e3) {
							throw new StopTest(takeScreenShot(), e3);
						}
					}
				}
			}
		}
		
		
		return result;
	}

}
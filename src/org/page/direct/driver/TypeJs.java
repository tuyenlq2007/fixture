package org.page.direct.driver;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TypeJs extends WebApi {
	private final String enterValue;

	public TypeJs(String enterValue, String object_repository) {
		super(object_repository);
		this.enterValue = enterValue;
	}
/*
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
*/
	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		final StringSelection str = new StringSelection(enterValue);
		clipboard.setContents(str, null);
		/*final JavascriptExecutor executor = (JavascriptExecutor) driver;
		final String hasAngularFinishedScript = "try {var callback=arguments[arguments.length-1];"
				+ " if (!window.angular) { throw new Error('angular could not be found on the window');}if (angular.getTestability) {angular.getTestability(angular.element(document.body)).whenStable(callback); } else { if (!angular.element(angular.element(document.body)).injector()) { throw new Error('root element (' + 'body' + ') has no injector.' +' this may mean it is not inside ng-app.'); }}"
				+ "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);}catch(err) {}";

		try {
			
			 * driver.manage().timeouts().setScriptTimeout(15,
			 * TimeUnit.SECONDS); WebDriverWait wait = new WebDriverWait(driver,
			 * 15, 100); wait.until(angularHasFinishedProcessing());
			 
			driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
			executor.executeAsyncScript(hasAngularFinishedScript);
			final WebElement element = validateElement();
			element.click();
			element.clear();
			element.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
			element.click();
			driver.manage().timeouts().setScriptTimeout(0, TimeUnit.SECONDS);
		} catch (final Exception e) {
			try {
				final WebElement element = validateElement();
				element.clear();
				element.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
			} catch (final Exception e1) {*/
				try {
					final WebElement element = validateElement();
					element.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
				} catch (final Exception e2) {
					throw new StopTest(takeScreenShot(), e2);
				}
			//}
		//}
		return result;
	}

}
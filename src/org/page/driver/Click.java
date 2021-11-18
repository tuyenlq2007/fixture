package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Click extends PageApi {

	public Click(String object_repository) {
		super(object_repository);
	}

	/*
	 * public ExpectedCondition<Boolean> angularHasFinishedProcessing() { return
	 * new ExpectedCondition<Boolean>() {
	 *
	 * @Override public Boolean apply(WebDriver driver) { String
	 * hasAngularFinishedScript =
	 * "try {var callback = arguments[arguments.length - 1];\n" +
	 * "var el = document.querySelector('html');\n" + "if (!window.angular) {\n"
	 * + "    callback('false')\n" + "}\n" + "if (angular.getTestability) {\n" +
	 * "    angular.getTestability(el).whenStable(function(){callback('true')});\n"
	 * + "} else {\n" + "    if (!angular.element(el).injector()) {\n" +
	 * "        callback('false')\n" + "    }\n" +
	 * "    var browser = angular.element(el).injector().get('$browser');\n" +
	 * "    browser.notifyWhenNoOutstandingRequests(function(){callback('true')});\n"
	 * + "} }catch (e){'true'};";
	 *
	 *
	 * JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
	 * String isProcessingFinished =
	 * javascriptExecutor.executeAsyncScript(hasAngularFinishedScript).toString(
	 * );
	 *
	 * return Boolean.valueOf(isProcessingFinished); } }; }
	 */

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";

		try {
			/*
			 * driver.manage().timeouts().setScriptTimeout(15,
			 * TimeUnit.SECONDS); WebDriverWait wait = new WebDriverWait(driver,
			 * 3, 20); wait.until(angularHasFinishedProcessing());
			 */
			final WebElement element = validateElement();
			element.click();
		} catch (final Exception e) {
			try {
				final WebElement element = validateElement();
				final JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].scrollIntoView();", element);
				executor.executeScript("arguments[0].click();", element);
			} catch (final Exception e3) { // or your specific exception
				throw new StopTest(takeScreenShot(), e3);
			}
		}
		return result;
	}
}

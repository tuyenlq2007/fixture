package org.page.direct.driver;

import java.util.concurrent.TimeUnit;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClickAt extends WebApi {

	public ClickAt(String object_repository) {
		super(object_repository);
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
		final String result = "SUCCESS";
		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		final String hasAngularFinishedScript = "try {var callback=arguments[arguments.length-1];"
				+ " if (!window.angular) { throw new Error('angular could not be found on the window');}if (angular.getTestability) {angular.getTestability(angular.element(document.body)).whenStable(callback); } else { if (!angular.element(angular.element(document.body)).injector()) { throw new Error('root element (' + 'body' + ') has no injector.' +' this may mean it is not inside ng-app.'); }}"
				+ "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);}catch(err) {}";

		try {
			 driver.manage().timeouts().setScriptTimeout(60,
			 TimeUnit.SECONDS);
			 WebDriverWait wait = new WebDriverWait(driver, 3, 20);
			 wait.until(angularHasFinishedProcessing());
			driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
			executor.executeAsyncScript(hasAngularFinishedScript);
			final WebElement element = validateElement();
			element.click();
			driver.manage().timeouts().setScriptTimeout(0, TimeUnit.SECONDS);
		} catch (final Exception e) {
			try {
				final WebElement element = validateElement();
				executor.executeScript("arguments[0].scrollIntoView();", element);
				element.click();
			} catch (final Exception e1) {
				try {
					final WebElement element = validateElement();
					executor.executeScript("arguments[0].click();", element);
				} catch (final Exception e2) {
					try {
						final WebElement element = validateElement();
						element.click();
					} catch (final Exception e3) { // or your specific exception
						throw new StopTest(takeScreenShot(), e3);
					}
				}
			}
		}
		return result;
	}
}

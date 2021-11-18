package org.page.direct.driver;

import java.util.concurrent.TimeUnit;


import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChooseByText extends WebApi {
	private final String text;

	public ChooseByText(String object_repository, String text) {
		super(object_repository);
		this.text = text;
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
			driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
			executor.executeAsyncScript(hasAngularFinishedScript);
			WebDriverWait wait = new WebDriverWait(driver, 30, 2000);
			wait.until(angularHasFinishedProcessing());
			
			final WebElement element = validateElement();
			final Select select = new Select(element);
			if (select.isMultiple()) {
				select.deselectAll();
			} 
			select.selectByVisibleText(text);
			WebElement option = select.getFirstSelectedOption();
			String defaultItem = option.getText();
			if (!defaultItem.toUpperCase().equals(text.toUpperCase())) {
				Thread.sleep(3000);			
				wait.until(angularHasFinishedProcessing());
				select.selectByVisibleText(text);
			}
		}catch (final Exception e) {
			try {
				Thread.sleep(3000);
				final WebElement element1 = validateElement();
				final Select select1 = new Select(element1);
				if (select1.isMultiple()) {
					select1.deselectAll();		
				} 
				select1.selectByVisibleText(text);
			} catch (final Exception e1) {
				throw new StopTest(takeScreenShot(), e1);
			}
			
		}
		
		return result;
	}

}
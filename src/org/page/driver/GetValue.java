package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class GetValue extends PageApi {
	public GetValue(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws StopTest {
		String result = "";
		try {
			final WebElement element = validateElement();
			final JavascriptExecutor executor = (JavascriptExecutor) driver;
			result = (String) executor.executeScript("return arguments[0].value;", element);
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}
}

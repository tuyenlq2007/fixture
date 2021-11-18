package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Click extends WebApi {

	public Click(String object_repository) {
		super(object_repository);
	}

	

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";

		try {
			
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

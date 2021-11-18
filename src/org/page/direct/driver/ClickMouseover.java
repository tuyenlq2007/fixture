package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ClickMouseover extends WebApi {

	public ClickMouseover(String object_repository) {
		super(object_repository);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";

		try {
			Actions mouseover = new Actions(driver);			
			final WebElement element = validateElement();
			mouseover.moveToElement(element).build().perform();
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

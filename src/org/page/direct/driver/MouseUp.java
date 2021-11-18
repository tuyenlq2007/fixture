package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseUp extends WebApi {

	public MouseUp(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";

		try {

			final WebElement element = validateElement();
			Actions builder = new Actions(driver);
			 builder.moveToElement(element).release().perform();
		} catch (final Exception e3) { // or your specific exception
			throw new StopTest(takeScreenShot(), e3);
		}
		return result;
	}
}

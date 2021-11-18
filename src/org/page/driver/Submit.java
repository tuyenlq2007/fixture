package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;

public class Submit extends PageApi {

	public Submit(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		try {
			final WebElement element = validateElement();

			element.submit();

		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}

}
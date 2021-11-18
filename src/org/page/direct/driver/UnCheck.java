package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;

public class UnCheck extends WebApi {

	public UnCheck(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		try {
			final WebElement element = validateElement();

			if (element.isSelected()) {
				element.click();
			}

		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}

}
package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;

public class GetText extends WebApi {
	public GetText(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws StopTest {
		String result = "";
		try {
			final WebElement element = validateElement();
			result = element.getText();
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}
}

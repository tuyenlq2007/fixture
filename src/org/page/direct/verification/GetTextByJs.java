package org.page.direct.verification;

import org.api.WebVerificationApi;
import org.openqa.selenium.WebElement;

public class GetTextByJs extends WebVerificationApi {
	public GetTextByJs(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws Exception {
		String value = "";
		try {
			final WebElement element = validateElement();
			value = (String) js.executeScript("return arguments[0].textContent;", element);

		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new Exception(takeScreenShot(), e);
		}
		return value;

	}
}

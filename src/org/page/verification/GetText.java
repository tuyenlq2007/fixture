package org.page.verification;

import org.api.PageVerificationApi;
import org.openqa.selenium.WebElement;

public class GetText extends PageVerificationApi {
	public GetText(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws Exception {
		String result = "";
		try {
			final WebElement element = validateElement();
			result = element.getText();
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new Exception(takeScreenShot(), e);
		}
		return result;
	}
}
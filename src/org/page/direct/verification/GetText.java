package org.page.direct.verification;

import org.api.WebVerificationApi;
import org.openqa.selenium.WebElement;

public class GetText extends WebVerificationApi {
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
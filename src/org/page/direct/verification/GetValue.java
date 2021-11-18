package org.page.direct.verification;

import org.api.WebVerificationApi;
import org.openqa.selenium.WebElement;

public class GetValue extends WebVerificationApi {
	public GetValue(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws Exception {
		String value = "";
		try {
			final WebElement element = validateElement();
			value = element.getAttribute("value");
		} catch (final Exception e) { // or your specific exception
			throw new Exception(takeScreenShot(), e);
		}
		return value;
	}
}
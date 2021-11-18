package org.page.verification;

import org.api.PageVerificationApi;
import org.openqa.selenium.WebElement;

public class IsVisible extends PageVerificationApi {
	public IsVisible(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws Exception {
		final WebElement element = validateElement();
		if (!element.isDisplayed()) {
			return "NO";
		}
		return "YES";
	}
}
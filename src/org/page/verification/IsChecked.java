package org.page.verification;

import org.api.PageVerificationApi;
import org.openqa.selenium.WebElement;

public class IsChecked extends PageVerificationApi {
	public IsChecked(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws Exception {
		final WebElement element = validateElement();
		if (!element.isSelected()) {
			return "NO";
		}
		return "YES";
	}
}
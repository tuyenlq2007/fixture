package org.page.direct.verification;

import org.api.WebVerificationApi;
import org.openqa.selenium.WebElement;

public class IsChecked extends WebVerificationApi {
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
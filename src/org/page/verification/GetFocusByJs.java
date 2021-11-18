package org.page.verification;

import org.api.PageVerificationApi;
import org.openqa.selenium.WebElement;

public class GetFocusByJs extends PageVerificationApi {
	public GetFocusByJs(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws Exception {
		String value = "";
		try {
			final WebElement element = validateElement();
			value = (String) js.executeScript(
					"if (arguments[0].isEqualNode(document.activeElement)) {return 'true'} else {return 'false'};",
					element);

		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new Exception(takeScreenShot(), e);
		}
		return value;

	}
}
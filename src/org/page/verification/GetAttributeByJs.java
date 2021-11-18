package org.page.verification;

import org.api.PageVerificationApi;
import org.openqa.selenium.WebElement;

public class GetAttributeByJs extends PageVerificationApi {
	private final String attrName;

	public GetAttributeByJs(String object_repository, String attrName) {
		super(object_repository);
		this.attrName = attrName;
	}

	@Override
	public String execute() throws Exception {
		String value = "";
		try {
			final WebElement element = validateElement();
			value = (String) js.executeScript("return arguments[0].getAttribute('" + attrName + "');", element);

		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new Exception(takeScreenShot(), e);
		}
		return value;

	}
}

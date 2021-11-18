package org.page.direct.verification;

import org.api.WebVerificationApi;
import org.openqa.selenium.WebElement;

public class GetAttributeByJs extends WebVerificationApi {
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

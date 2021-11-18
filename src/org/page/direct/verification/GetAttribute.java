package org.page.direct.verification;

import org.api.WebVerificationApi;
import org.openqa.selenium.WebElement;

public class GetAttribute extends WebVerificationApi {
	private final String attrName;

	public GetAttribute(String object_repository, String attrName) {
		super(object_repository);
		this.attrName = attrName;
	}

	@Override
	public String execute() throws Exception {
		String result = "";
		try {
			final WebElement element = validateElement();
			result = element.getAttribute(attrName);
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new Exception(takeScreenShot(), e);
		}
		return result;

	}

}
package org.page.verification;

import org.api.PageVerificationApi;
import org.openqa.selenium.WebElement;

public class GetAttribute extends PageVerificationApi {
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
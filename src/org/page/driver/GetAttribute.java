package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;

public class GetAttribute extends PageApi {
	private final String attrName;

	public GetAttribute(String object_repository, String attrName) {
		super(object_repository);
		this.attrName = attrName;
	}

	@Override
	public String execute() throws StopTest {
		String result = "";
		try {
			final WebElement element = validateElement();
			result = element.getAttribute(attrName);
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}

}
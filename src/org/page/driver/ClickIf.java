package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;

public class ClickIf extends PageApi {
	final String object_repository2;

	public ClickIf(String object_repository1, String object_repository2) {
		super(object_repository1);
		this.object_repository2 = object_repository2;
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		try {
			validateElement();
			final WebElement element2 = validateElement(object_repository2);
			element2.click();
		} catch (final Exception e) {
			// Do nothing
		}
		return result;
	}
}

package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;

public class SendKey extends PageApi {
	private final String enterValue;

	public SendKey(String enterValue, String object_repository) {
		super(object_repository);
		this.enterValue = enterValue;		
	}

	@Override
	public String execute() throws StopTest {
		String result = "SUCCESS";
		try {

			final WebElement element = validateElement();
			element.sendKeys(enterValue);
		} catch (final Exception e) {

			throw new StopTest(takeScreenShot(), e);
		}

		return result;
	}

}
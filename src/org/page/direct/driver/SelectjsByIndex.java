package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class SelectjsByIndex extends WebApi {
	private final String index;

	public SelectjsByIndex(String object_repository, String index) {
		super(object_repository);
		this.index = index;
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		try {
			final WebElement element = validateElement();
			((JavascriptExecutor) driver).executeScript("arguments[0].selectedIndex = '" + index + "';", element);
		} catch (final Exception e1) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e1);
		}
		return result;
	}

}

package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JsSetText extends WebApi {
	private final String enterValue;

	public JsSetText(String enterValue, String object_repository) {
		// TODO Auto-generated constructor stub
		super(object_repository);
		this.enterValue = enterValue;
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		try {
			final WebElement element = validateElement();

			final JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].textContent ='" + enterValue + "'", element);

		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}

}

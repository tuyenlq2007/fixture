package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class ClickAtWithTimeoutPollingEvery extends PageApi {
	private int timeout;
	private final int polling;

	public ClickAtWithTimeoutPollingEvery(String locator, String timeout, String polling) {
		super(locator);
		this.timeout = Integer.parseInt(timeout);
		this.polling = Integer.parseInt(polling);
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		boolean bFound = false;
		while (timeout > 0 && !bFound) {
			try {
				validateElement();
				bFound = true;
			} catch (final Exception e) {
				timeout = timeout - polling;
			}
		}
		
		if (!bFound) {
			throw new StopTest(takeScreenShot(), new Exception("No element found"));
		}
				
		try {
			final WebElement element = validateElement();
			element.click();

		} catch (final Exception e) {
			try {
				final WebElement element = validateElement();
				final JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].scrollIntoView();", element);
				element.click();

			} catch (final Exception e1) {
				try {
					final WebElement element = validateElement();
					final JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", element);
				} catch (final Exception e2) {
					throw new StopTest(takeScreenShot(), e2);
				}
			}
		}

		return result;
	}
}

package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class ClickAndWaitForLoading extends WebApi {

	final String object_repository2;

	public ClickAndWaitForLoading(String object_repository1, String object_repository2) {
		super(object_repository1);
		this.object_repository2 = object_repository2;
	}

	private void click() throws StopTest {

		try {
			final WebElement element1 = validateElement();
			element1.click();
		} catch (final Exception e) {
			try {
				final WebElement element = validateElement();
				final JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].scrollIntoView();", element);
				executor.executeScript("arguments[0].click();", element);
			} catch (final Exception e3) { // or your specific exception
				throw new StopTest(takeScreenShot(), e3);
			}
		}
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		click();
		try {
			Thread.sleep(3000);
			validateElement(object_repository2);
		} catch (final Exception e) {
			return result;
		}

		try {
			Thread.sleep(10000);
			validateElement(object_repository2);
		} catch (final Exception e) {
			return result;
		}

		try {
			Thread.sleep(20000);
			validateElement(object_repository2);
		} catch (final Exception e) {
			return result;
		}

		throw new StopTest("Timeout Error: Click took longer than 30 seconds. See screenshot: " + takeScreenShot());

	}
}

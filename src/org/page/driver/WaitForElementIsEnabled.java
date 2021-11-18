package org.page.driver;

import java.util.concurrent.TimeUnit;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Predicate;

public class WaitForElementIsEnabled extends PageApi {
	private final String iTimeout;

	public WaitForElementIsEnabled(String iTimeout, String object_repository) {
		super(object_repository);
		this.iTimeout = iTimeout;
	}

	@Override
	public String execute() throws StopTest {
		try {
			Thread.sleep(3000);	
			final WebElement element = validateElement();
			final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
			wait.withTimeout(30000, TimeUnit.MILLISECONDS);
			wait.pollingEvery(Long.parseLong(iTimeout), TimeUnit.MILLISECONDS);
			wait.ignoring(NoSuchElementException.class);
			final Predicate<WebDriver> predicate = arg0 -> {
				final JavascriptExecutor executor = (JavascriptExecutor) arg0;
				final String result = (String) executor.executeScript("return arguments[0].disabled.toString();", element);
				return result != "true" ? false : true;
			};
			wait.until(predicate);
		} catch (final Exception e) {
			throw new StopTest(takeScreenShot(), e);
		}
		return "SUCCESS";
	}
}

package org.page.driver;

import java.util.concurrent.TimeUnit;

import org.api.PageApi;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Predicate;

public class WaitForCondition extends PageApi {
	private final String script;
	private final String iTimeout;

	public WaitForCondition(String iTimeout, String script) {
		this.script = script;
		this.iTimeout = iTimeout;
	}

	@Override
	public String execute() {
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.withTimeout(30000, TimeUnit.MILLISECONDS);
		wait.pollingEvery(Long.parseLong(iTimeout), TimeUnit.MILLISECONDS);
		wait.ignoring(NoSuchElementException.class);
		final Predicate<WebDriver> predicate = arg0 -> {
			final JavascriptExecutor executor = (JavascriptExecutor) arg0;
			final String result = (String) executor.executeScript(script);
			return result != "true" ? false : true;
		};
		wait.until(predicate);
		return "SUCCESS";
	}
}

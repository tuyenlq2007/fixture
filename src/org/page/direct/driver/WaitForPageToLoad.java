package org.page.direct.driver;

import org.api.WebApi;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import utils.Wait;
import utils.Wait.StopCondition;

public class WaitForPageToLoad extends WebApi {
	private static StopCondition checkByBodyLength(final WebDriver driver) {
		return new StopCondition() {

			private int prevLen = -1;
			private long prevTime = 0;

			@Override
			public boolean isSatisfied() {
				WebElement body;
				try {
					body = driver.findElement(By.tagName("body"));
				} catch (final NoSuchElementException e) {
					return false;
				}
				final String text = body.getText();
				if (text == null) {
					return false;
				}
				final int len = text.length();
				final long now = System.currentTimeMillis();
				if (prevLen == len) {
					return now - prevTime > 1000;
				}
				prevLen = len;
				prevTime = now;
				return false;
			}
		};
	}

	private static StopCondition checkByReadyState(final WebDriver driver) {
		return () -> {
			try {
				final Boolean result = (Boolean) ((JavascriptExecutor) driver)
						.executeScript("return document.readyState === 'complete';");
				if (result != null && result) {
					return true;
				}
			} catch (final Exception e) {
				// no operation.
			}
			return false;
		};
	}

	private static boolean isReadyStateSupported(WebDriver driver) {
		try {
			return isReadyStateSupportedInternal(driver);
		} catch (final WebDriverException e) {
			// no operation.
		}
		Wait.sleep(250);
		try {
			return isReadyStateSupportedInternal(driver);
		} catch (final WebDriverException e) {
			// no operation.
		}
		Wait.sleep(500);
		try {
			return isReadyStateSupportedInternal(driver);
		} catch (final WebDriverException e) {
			return false;
		}
	}

	private static boolean isReadyStateSupportedInternal(WebDriver driver) {
		final Boolean result = (Boolean) ((JavascriptExecutor) driver)
				.executeScript("return !!document['readyState'];");
		return result != null ? result : false;
	}

	private final String iTimeout;

	public WaitForPageToLoad(String iTimeout) {
		this.iTimeout = iTimeout;
	}

	@Override
	public String execute() {
		if (Long.parseLong(iTimeout) < 0) {
			return "Illegal timeout parameter: " + iTimeout;
		} else if (Long.parseLong(iTimeout) == 0) {
			return "SUCCESS";
		}

		// System.currentTimeMillis();
		// if (!(driver instanceof JavascriptExecutor)) {
		// return "WebDriver is not support JavaScript.";
		// }
		//
		// isReadyStateSupported(driver);
		// checkByReadyState(driver);
		// checkByBodyLength(driver);

		final long startTime = System.currentTimeMillis();
		if (!(driver instanceof JavascriptExecutor)) {
			return "WebDriver is not support JavaScript.";
		}
		final StopCondition condition = isReadyStateSupported(driver) ? checkByReadyState(driver)
				: checkByBodyLength(driver);
		if (!Wait.defaultInterval.wait(startTime, Long.parseLong(iTimeout), condition)) {
			return "Failed to load page within ms";
		}

		return "Success";
	}

	public boolean wait(long startTime, long timeout, StopCondition stopCondition) {
		if (stopCondition.isSatisfied()) {
			return true;
		}
		do {
			final long now = System.currentTimeMillis();
			long d = timeout - (now - startTime);
			if (d <= 0) {
				return false;
			}
			if (d > 5000) {
				d = 5000;
			}
			try {
				Thread.sleep(d);
			} catch (final InterruptedException e) { // TODO
				e.printStackTrace();
			}
		} while (!stopCondition.isSatisfied());
		return true;
	}
}

package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class TryToClickUntilElementIsEnabledWithTimeoutPollingEvery extends WebApi {
	private int timeout;
	private final int polling;
	private final String element2;

	public TryToClickUntilElementIsEnabledWithTimeoutPollingEvery(final String element1, final String element2, final String timeout, final String polling) {
		super(element1);
		this.timeout = Integer.parseInt(timeout);
		this.polling = Integer.parseInt(polling);
		this.element2 = element2;
	}


@Override
public String execute() throws StopTest {
	final String result = "SUCCESS";
	boolean bFound = false;
	while (timeout > 0 && !bFound) {
		try {
			final WebElement element = validateElement();
			try {				
				element.click();
			} catch (final Exception e) {
				try {
					final JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].scrollIntoView();", element);
					element.click();

				} catch (final Exception e1) {
					try {
						final JavascriptExecutor executor = (JavascriptExecutor) driver;
						executor.executeScript("arguments[0].click();", element);
					} catch (final Exception e2) {
						throw new StopTest(takeScreenShot(), e2);
					}
				}
			}
			validateElement(element2);
			bFound = true;
		} catch (final Exception e) {
			timeout = timeout - polling;
		}
	}
	
	if (!bFound) {
		throw new StopTest(takeScreenShot(), new Exception("No element found"));
	}
			
	return result;
}
}

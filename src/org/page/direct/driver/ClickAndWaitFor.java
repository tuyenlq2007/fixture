package org.page.direct.driver;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;

public class ClickAndWaitFor extends WebApi {
	private final String waitLocator;
	public ClickAndWaitFor(String locator, String waitLocator) {
		super(locator);
		this.waitLocator = waitLocator;
	}
	@Override
	public String execute() throws StopTest {
		String result = "SUCCESS";
		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		String element = returnXpath();
		String scriptInput = "function getElementByXpath(path) { return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}; getElementByXpath(\"actualxpath\").click ;";
		scriptInput = scriptInput.replaceAll("actualxpath", element);	
		try {
			executor.executeScript(scriptInput);
			waiforsync(waitLocator);
		} catch (final Exception e) {
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}

	private void waiforsync(String xpath) {
		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		final String script = "function getElementByXpath(path) {  return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}; if (getElementByXpath('"
				+ xpath + "') == null){ return false } else {return true} ;";
		boolean result = (boolean) executor.executeScript(script);
		Long timeout = (long) 18000;
		while (result && timeout > 0) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result = (boolean) executor.executeScript(script);
			timeout = timeout - 3000;
		}
	}
}

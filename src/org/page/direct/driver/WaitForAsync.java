package org.page.direct.driver;

import org.api.WebApi;
import org.openqa.selenium.JavascriptExecutor;

public class WaitForAsync extends WebApi {
	private final String xpath;
	private final String iTimeout;

	public WaitForAsync(String iTimeout, String xpath) {
		this.xpath = xpath;
		this.iTimeout = iTimeout;
	}

	@Override
	public String execute() {
		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		String script = "function getElementByXpath(path) {  return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}; if (getElementByXpath(\"actualxpath\") == null){ return false } else {return true} ;";
		script = script.replaceAll("actualxpath", xpath);
		boolean result = (boolean) executor.executeScript(script);
		Long timeout = Long.parseLong(iTimeout);
		while (result && timeout > 0) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result = (boolean) executor.executeScript(script);
			timeout = timeout - 3000;
		}
		return "SUCCESS";
	}
}

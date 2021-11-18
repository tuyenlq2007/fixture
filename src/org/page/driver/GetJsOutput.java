package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;

public class GetJsOutput extends PageApi {
	String js;

	public GetJsOutput(String enterJs) {
		js = enterJs;
	}

	@Override
	public String execute() throws StopTest {
		String result = "";
		try {
			// result = (String) js.executeScript(this.jsScript);
			result = (String) ((JavascriptExecutor) driver).executeScript(js);
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}
}

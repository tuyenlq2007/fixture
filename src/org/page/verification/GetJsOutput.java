package org.page.verification;

import org.api.PageVerificationApi;
import org.openqa.selenium.JavascriptExecutor;

public class GetJsOutput extends PageVerificationApi {
	private final String jsScript;

	public GetJsOutput(String jsScript) {
		super(jsScript);
		this.jsScript = jsScript;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute() throws Exception {
		String result = "";
		try {
			// result = (String) js.executeScript(this.jsScript);
			result = (String) ((JavascriptExecutor) driver).executeScript(jsScript);
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new Exception(takeScreenShot(), e);
		}
		return result;

	}
}

package org.page.direct.driver;

import org.api.WebApi;
import org.openqa.selenium.JavascriptExecutor;

public class RunScript extends WebApi {
	private final String script;

	public RunScript(String script) {
		this.script = script;
	}

	@Override
	public String execute() {
		final String result = "SUCCESS";
		((JavascriptExecutor) driver).executeScript(script);
		return result;
	}
}
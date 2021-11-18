package org.page.driver;

import org.api.PageApi;
import org.openqa.selenium.JavascriptExecutor;

public class RunScript extends PageApi {
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
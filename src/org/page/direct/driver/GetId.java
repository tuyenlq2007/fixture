package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class GetId extends WebApi {
	public GetId(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws StopTest {
		final WebElement element = validateElement();
		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		return (String) executor.executeScript("return arguments[0].id;", element);
	}
}

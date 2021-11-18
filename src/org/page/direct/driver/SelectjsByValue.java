package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class SelectjsByValue extends WebApi {
	private final String value;

	public SelectjsByValue(String object_repository, String value) {
		super(object_repository);
		this.value = value;
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		try {
			final WebElement element = validateElement();
			((JavascriptExecutor) driver).executeScript(
					"function setOption(selectElement, value) {    var options = selectElement.options;    for (var i = 0, optionsLength = options.length; i < optionsLength; i++) {        if (options[i].value == value) {            selectElement.selectedIndex = i;            return true;        }    }    return false;};"
							+ "setOption(arguments[0],'" + value + "');",
					element);
		} catch (final Exception e1) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e1);
		}
		return result;
	}
}

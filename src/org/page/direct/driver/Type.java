package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Type extends WebApi {
	private final String enterValue;

	public Type(String object_repository, String enterValue) {
		super(object_repository);
		this.enterValue = enterValue;
	}


	@Override
	public String execute() throws StopTest {
		String result = "SUCCESS";
		try {
			
			final WebElement element = validateElement();
			final String tagName = element.getTagName().toLowerCase();
			switch (tagName) {
			case "input":
				final String type = element.getAttribute("type");
				if (type != null && "file".equalsIgnoreCase(type)) {
					result = "You should be using attachFile to set the value of a file input element";
				}
				element.clear();
				element.sendKeys(enterValue);
				break;
			default:
				((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
				element.sendKeys(enterValue);
				break;
			}
		} catch (final Exception e) {
			try {
				final WebElement element = validateElement();
				final String tagName = element.getTagName().toLowerCase();
				switch (tagName) {
				case "input":
					final String type = element.getAttribute("type");
					if (type != null && "file".equalsIgnoreCase(type)) {
						result = "You should be using attachFile to set the value of a file input element";
					}
					element.clear();
					element.sendKeys(enterValue);
					break;
				default:
					((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
					element.sendKeys(enterValue);
					break;
				}
			} catch (final Exception e1) {
				throw new StopTest(takeScreenShot(), e1);
			}

		}
		return result;
	}

}
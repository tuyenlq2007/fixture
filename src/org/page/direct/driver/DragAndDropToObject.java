package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DragAndDropToObject extends WebApi {
	private final String object_repository2;

	public DragAndDropToObject(String object_repository1, String object_repository2) {
		super(object_repository1);
		this.object_repository2 = object_repository2;
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		try {
			final WebElement elementFrom = validateElement();
			final WebElement elementTo = validateElement(object_repository2);

			new Actions(driver).dragAndDrop(elementFrom, elementTo).perform();

		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}

}
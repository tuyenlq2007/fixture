package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DragAndDrop extends WebApi {
	private final String coordinate;

	public DragAndDrop(String object_repository, String coordinate) {
		super(object_repository);
		this.coordinate = coordinate;
	}

	@Override
	public String execute() throws StopTest {
		String result = "SUCCESS";
		try {
			final WebElement element = validateElement();

			final String[] parts = coordinate.split("\\s*,\\s*", 2);
			int xDelta;
			int yDelta;
			try {
				xDelta = Integer.parseInt(parts[0].trim());
				yDelta = Integer.parseInt(parts[1].trim());
				new Actions(driver).dragAndDropBy(element, xDelta, yDelta).perform();
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				result = "Invalid movements: " + parts.toString();
			}

		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}

}
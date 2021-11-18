package org.page.driver;

import java.awt.Robot;
import java.awt.event.InputEvent;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

public class DeClick extends PageApi {
	public DeClick(String object_repository) {
		super(object_repository);
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		try {

			driver.manage().window().getSize().getHeight();
			driver.manage().window().getSize().getWidth();
			final WebElement element = validateElement();
			final Point absoluteCor = element.getLocation();
			// final Point cor = element.getLocation();
			final Robot bot = new Robot();
			bot.mouseMove(-absoluteCor.x, -absoluteCor.y);
			bot.mousePress(InputEvent.BUTTON1_MASK);
			bot.mouseRelease(InputEvent.BUTTON1_MASK);
			// element.click();
		} catch (final Exception e) {
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}
}

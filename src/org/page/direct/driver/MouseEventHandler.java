package org.page.direct.driver;

import java.util.List;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseEventHandler extends WebApi {
	// Use createEvent & initMouseEvent for PhantomJS.
	private static final String NEW_MOUSE_EVENT = "var newMouseEvent = function(element, event, init) {" + "try {"
			+ "return new MouseEvent(event, init);" + "} catch (e) {" + "var e = document.createEvent('MouseEvents');"
			+ "e.initMouseEvent(event, true, true, window, 0, "
			+ "init.screenX, init.screenY, init.clientX, init.clientY, "
			+ "init.ctrlKey, init.altKey, init.shiftKey, init.metaKey, init.button, element);" + "return e;" + "}"
			+ "};";

	private static final String FIRE_MOUSE_OUT_EVENT = "(function(element) {" + NEW_MOUSE_EVENT
			+ "element.dispatchEvent(newMouseEvent(element, 'mouseleave', {}));"
			+ "element.dispatchEvent(newMouseEvent(element, 'mouseout', {}));" + "})(arguments[0])";

	private static Point calcOffset(int vpWidth, int vpHeight, Point elemLocation, Dimension elemSize) {
		int xOffset = elemSize.width / 2;
		int yOffset = elemSize.height / 2;
		if (elemLocation.x + elemSize.width <= 0 || elemLocation.x >= vpWidth) {
			/* out of viewport */;
		} else if (elemLocation.x + xOffset < 0) {
			xOffset = 0;
		} else if (elemLocation.x + xOffset >= vpWidth) {
			xOffset = vpWidth - 1;
		}
		if (elemLocation.y + elemSize.height <= 0 || elemLocation.y >= vpHeight) {
			/* out of viewport */;
		} else if (elemLocation.y + yOffset < 0) {
			yOffset = 0;
		} else if (elemLocation.y + yOffset >= vpHeight) {
			yOffset = vpHeight - 1;
		}
		return new Point(xOffset, yOffset);
	}

	private static Point calcOffsetOutsideElement(int vpWidth, int vpHeight, Point elemLocation, Dimension elemSize) {
		int xOffset, yOffset;
		int inside = 0;
		if (elemLocation.x - 1 >= 0) {
			xOffset = -1;
		} else if (elemLocation.x + elemSize.width < vpWidth) {
			xOffset = elemSize.width;
		} else {
			xOffset = vpWidth / 2;
			inside++;
		}
		if (elemLocation.y - 1 >= 0) {
			yOffset = -1;
		} else if (elemLocation.y + elemSize.height < vpHeight) {
			yOffset = elemSize.height;
		} else {
			yOffset = vpHeight / 2;
			inside++;
		}
		return inside < 2 ? new Point(xOffset, yOffset) : null;
	}

	private static Point coordToPoint(String coordString) {
		final String[] pair = coordString.trim().split("\\s*,\\s*");
		final int x = (int) Double.parseDouble(pair[0]);
		final int y = (int) Double.parseDouble(pair[1]);
		return new Point(x, y);
	}

	@SuppressWarnings("unchecked")
	private static <T> T eval(WebDriver driver, String script, Object... args) {
		return (T) ((JavascriptExecutor) driver).executeScript(script, args);
	}

	private final String mouseEventType;

	public MouseEventHandler(String mouseEventType, String object_repository) {
		// TODO Auto-generated constructor stub
		super(object_repository);
		this.mouseEventType = mouseEventType;
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		final List<Long> viewportSize = eval(driver,
				"return [document.documentElement.clientWidth, document.documentElement.clientHeight];");
		final int vpWidth = viewportSize.get(0).intValue();
		final int vpHeight = viewportSize.get(1).intValue();

		try {
			final WebElement element = validateElement();
			final Dimension elemSize = element.getSize();
			final Point elemLocation = element.getLocation();
			final Actions actions = new Actions(driver);
			Point coord;
			switch (mouseEventType) {
			case "MOUSE_OVER":
			case "MOUSE_MOVE":
				coord = calcOffset(vpWidth, vpHeight, elemLocation, elemSize);
				actions.moveToElement(element, coord.x, coord.y);
				break;
			case "MOUSE_OUT":
				coord = calcOffsetOutsideElement(vpWidth, vpHeight, elemLocation, elemSize);
				if (coord != null) {
					actions.moveToElement(element, coord.x, coord.y);
				} else {
					eval(driver, FIRE_MOUSE_OUT_EVENT, element);
					return null;
				}
				break;
			case "MOUSE_MOVE_AT":
				coord = coordToPoint("10,10");
				actions.moveToElement(element, coord.x, coord.y);
				break;
			case "MOUSE_DOWN":
				coord = calcOffset(vpWidth, vpHeight, elemLocation, elemSize);
				actions.moveToElement(element, coord.x, coord.y).clickAndHold();
				break;
			case "MOUSE_DOWN_AT":
				coord = coordToPoint("10,10");
				actions.moveToElement(element, coord.x, coord.y).clickAndHold();
				break;
			case "MOUSE_UP":
				coord = calcOffset(vpWidth, vpHeight, elemLocation, elemSize);
				actions.moveToElement(element, coord.x, coord.y).release();
				break;
			case "MOUSE_UP_AT":
				coord = coordToPoint("10,10");
				actions.moveToElement(element, coord.x, coord.y).release();
				break;
			default:
			}
			/*
			 * JavascriptExecutor executor = (JavascriptExecutor) driver; final
			 * String script = "keyCode= " + this.keycode
			 * +";eventType = 'keyup';	evt = document.createEvent('Events');    evt.initEvent(eventType, true, true);    evt.view = window;    evt.shiftKey = true;    evt.metaKey = true;    evt.altKey = true;    evt.ctrlKey = true;    evt.keyCode = keyCode;    evt.which = keyCode;    evt.charCode = (eventType === 'keypress') ? keyCode : 0; arguments[0].dispatchEvent(evt);"
			 * ; executor.executeScript(script, element);
			 */
			actions.build().perform();
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}
}

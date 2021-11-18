package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class KEYPRESS extends WebApi {
	private final String keycode;

	public KEYPRESS(String keycode, String object_repository) {
		// TODO Auto-generated constructor stub
		super(object_repository);
		this.keycode = keycode;
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		try {
			final WebElement element = validateElement();
			final JavascriptExecutor executor = (JavascriptExecutor) driver;
			final String script = "keyCode= " + keycode
					+ ";eventType = 'keypress';	evt = document.createEvent('Events');    evt.initEvent(eventType, true, true);    evt.view = window;    evt.shiftKey = true;    evt.metaKey = true;    evt.altKey = true;    evt.ctrlKey = true;    evt.keyCode = keyCode;    evt.which = keyCode;    evt.charCode = (eventType === 'keypress') ? keyCode : 0; arguments[0].dispatchEvent(evt);";
			executor.executeScript(script, element);
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}
}

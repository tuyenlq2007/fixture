package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class PressKeyCode extends WebApi {
	private final int keycode;

	public PressKeyCode(int keycode) {
		this.keycode = keycode;
	}


	@SuppressWarnings("deprecation")
	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		try {
			//final WebElement element = validateElement();
			((AndroidDriver<MobileElement>) driver).pressKeyCode(keycode);
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}
}

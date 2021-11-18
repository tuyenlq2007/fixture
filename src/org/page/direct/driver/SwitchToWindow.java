package org.page.direct.driver;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.api.WebApi;
import org.exception.StopTest;

public class SwitchToWindow extends WebApi {
	private final String regex;

	public SwitchToWindow(String regex) {
		super();
		this.regex = regex;
	}

	@Override
	public String execute() throws StopTest {
		String result = "SUCCESS";
		Pattern p;
		Matcher m;
		try {
			final Set<String> windows = driver.getWindowHandles();

			for (final String window : windows) {
				driver.switchTo().window(window);
				System.out.println(String.format("#switchToWindow() : title=%s ; url=%s", driver.getTitle(),
						driver.getCurrentUrl()));

				p = Pattern.compile(regex);
				m = p.matcher(driver.getTitle());

				if (m.find()) {
					return result;
				} else {
					m = p.matcher(driver.getCurrentUrl());
					if (m.find()) {
						return result;
					}
				}
			}

			result = "Could not switch to window with title / url: " + regex;
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new StopTest(takeScreenShot(), e);
		}
		return result;
	}

}
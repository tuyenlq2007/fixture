package org.page.driver;

import org.apache.commons.lang3.StringUtils;
import org.api.PageApi;

public class Pause extends PageApi {
	private final String pause_msec;

	public Pause(String pause_msec) {
		this.pause_msec = pause_msec;
	}

	@Override
	public String execute() {
		final String result = "SUCCESS";
		final String pauseMSec = pause_msec;
		if (StringUtils.isBlank(pauseMSec)) {
			return "pause is ignored: empty time.";
		}
		try {
			Thread.sleep(Long.parseLong(pauseMSec));
			return result;
		} catch (final NumberFormatException e) {
			return "pause is ignored: invalid time: ";
		} catch (final InterruptedException e) {
			return e.toString();
		}
	}
}
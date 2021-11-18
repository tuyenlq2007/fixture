package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;

public class GetDigit extends PageApi {
	String value;

	public GetDigit(String enterValue) {
		value = enterValue;
	}

	@Override
	public String execute() throws StopTest {
		final String result = value.replaceAll("\\D+", "");
		return result;
	}

}
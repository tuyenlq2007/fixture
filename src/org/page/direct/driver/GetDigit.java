package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;

public class GetDigit extends WebApi {
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
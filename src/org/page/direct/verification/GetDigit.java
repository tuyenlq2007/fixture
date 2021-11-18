package org.page.direct.verification;

import org.api.WebVerificationApi;

public class GetDigit extends WebVerificationApi {
	String value;

	public GetDigit(String value) {
		super(value);
		this.value = value;
	}

	@Override
	public String execute() throws Exception {
		String result = "";
		try {
			result = value.replaceAll("\\D+", "");
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new Exception(takeScreenShot(), e);
		}
		return result;

	}

}
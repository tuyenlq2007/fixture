package org.page.verification;

import org.api.PageVerificationApi;

public class GetDigit extends PageVerificationApi {
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
package org.page.direct.driver;

import java.util.Random;

import org.api.WebApi;
import org.exception.StopTest;

public class GetRandomNumber extends WebApi {
	String entervalue;

	public GetRandomNumber(String value) {
		// TODO Auto-generated constructor stub
		entervalue = value;
	}

	@Override
	public String execute() throws StopTest {
		final char[] chars = "0123456789".toCharArray();
		final StringBuilder sb = new StringBuilder();
		final Random random = new Random();
		for (int i = 0; i < Integer.parseInt(entervalue); i++) {
			final char c = chars[random.nextInt(chars.length)];
			sb.append(Character.toUpperCase(c));
		}
		final String output = sb.toString();
		return output;
	}
}

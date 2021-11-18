package org.page.driver;

import java.util.Random;

import org.api.PageApi;
import org.exception.StopTest;

public class GetRandom extends PageApi {
	String entervalue;

	public GetRandom(String value) {
		// TODO Auto-generated constructor stub
		entervalue = value;
	}

	@Override
	public String execute() throws StopTest {
		final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
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

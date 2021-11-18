package com.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.page.verification.PageVerification;

public class FixtureGetValue {
	private final List<List<List<String>>> getContent;

	public FixtureGetValue(String element) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		final String text = new PageVerification(element).getValue();
		// String text =
		// DriverPool.INSTANCE.getDriver(1).findElement(By.xpath("//label[contains(text(),'Home
		// Tel')]/parent::div/parent::div//li[contains(@class,
		// 'active')]/span[contains(@class,
		// 'dial-code')]")).getAttribute("value");
		getContent.add(Arrays.asList(Arrays.asList("Result", text)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
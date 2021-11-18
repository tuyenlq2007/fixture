package com.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.page.direct.verification.PageVerification;

public class VerifyValue {
	private final List<List<List<String>>> getContent;

	public VerifyValue(String element) throws Exception {
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
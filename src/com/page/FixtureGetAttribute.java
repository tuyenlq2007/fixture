package com.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.page.verification.PageVerification;

public class FixtureGetAttribute {
	private final List<List<List<String>>> getContent;

	public FixtureGetAttribute(String element, String attrName) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		final String text = new PageVerification(element, attrName).getAttribute();
		getContent.add(Arrays.asList(Arrays.asList("Result", text)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
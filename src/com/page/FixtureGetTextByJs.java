package com.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.page.verification.PageVerification;

public class FixtureGetTextByJs {
	private final List<List<List<String>>> getContent;

	public FixtureGetTextByJs(String element) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		final String text = new PageVerification(element).getTextByJs();
		getContent.add(Arrays.asList(Arrays.asList("Result", text)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
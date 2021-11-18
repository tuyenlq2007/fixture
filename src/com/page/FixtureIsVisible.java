package com.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.page.verification.PageVerification;

public class FixtureIsVisible {
	private final List<List<List<String>>> getContent;

	public FixtureIsVisible(String element) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		final String isVisible = new PageVerification(element).isVisible();
		getContent.add(Arrays.asList(Arrays.asList("Result", isVisible)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
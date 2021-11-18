package com.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FixtureExecution {
	private final List<List<List<String>>> getContent;

	public FixtureExecution() throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		final String text = "P";
		getContent.add(Arrays.asList(Arrays.asList("Result", text)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
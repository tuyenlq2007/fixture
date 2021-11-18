package com.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Verify {
	private final List<List<List<String>>> getContent;

	public Verify(String expectedresult) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		getContent.add(Arrays.asList(Arrays.asList("Result", expectedresult)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
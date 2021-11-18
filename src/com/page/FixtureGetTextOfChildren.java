package com.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.page.verification.PageVerification;

public class FixtureGetTextOfChildren {
	private final List<List<List<String>>> getContent;

	public FixtureGetTextOfChildren(String element) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		final List<String> textes = new PageVerification(element).getTextOfChildren();
		for (final String text : textes) {
			getContent.add(Arrays.asList(Arrays.asList("Result", text)));
		}
		// getContent.add(Arrays.asList(Arrays.asList("Result",
		// textes.get(1))));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
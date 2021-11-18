package com.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.page.direct.verification.PageVerification;

public class VerifyText {
	private final List<List<List<String>>> getContent;

	public VerifyText(String element) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		final String text = new PageVerification(element).getText().trim();
		getContent.add(Arrays.asList(Arrays.asList("Result", text)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
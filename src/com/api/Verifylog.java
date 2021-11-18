package com.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verifylog {
	private final List<List<List<String>>> getContent;

	public Verifylog(String tag) throws Exception {
		String expectedresult = "";
		getContent = new ArrayList<List<List<String>>>();
		try {
			// expectedresult = getAttribute(getFile("C:/ITS/log.txt"),tag);
			expectedresult = getFile(System.getenv("ITS") + "/log.txt");
			final String pattern1 = "<" + tag + ">";
			final String pattern2 = "</" + tag + ">";

			final Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			final Matcher m = p.matcher(expectedresult);
			while (m.find()) {
				getContent.add(Arrays.asList(Arrays.asList("Result", m.group(1))));
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			throw new Exception("can't find attribute in log");
		}

	}

	private String getFile(String strXMLFilename) throws IOException {
		@SuppressWarnings("resource")
		final BufferedReader br = new BufferedReader(new FileReader(new File(strXMLFilename)));
		String line;
		final StringBuilder sb = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sb.append(line.trim());
		}
		return sb.toString();
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
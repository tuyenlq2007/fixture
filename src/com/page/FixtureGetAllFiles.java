package com.page;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FixtureGetAllFiles {
	private final List<List<List<String>>> getContent;

	public FixtureGetAllFiles(String fileName) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		String text = "";
		// String FILE_NAME = System.getProperty("user.dir") + File.separator +
		// "FitNesseRoot" + File.separator + "files"
		// + File.separator + fileName;

		try {
			final File folder = new File(fileName);
			final File[] listOfFiles = folder.listFiles();
			for (final File listOfFile : listOfFiles) {
				if (listOfFile.isFile()) {
					text = text + listOfFile.getName() + ";";
				} else if (listOfFile.isDirectory()) {
					text = text + listOfFile.getName() + ";";
				}
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
		getContent.add(Arrays.asList(Arrays.asList("Result", text)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}

}
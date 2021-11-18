package com.page;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FixtureGetTextFromFile {
	private final List<List<List<String>>> getContent;

	public FixtureGetTextFromFile(String fileName) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		String text = "";
		final String filepath = System.getProperty("user.dir") + File.separator + "FitNesseRoot" + File.separator
				+ "files" + File.separator + fileName;

		BufferedReader br = null;
		FileReader fr = null;

		try {

			// br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(filepath);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				text = sCurrentLine;
			}

		} catch (final IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null) {
					br.close();
				}

				if (fr != null) {
					fr.close();
				}

			} catch (final IOException ex) {

				ex.printStackTrace();

			}

		}

		getContent.add(Arrays.asList(Arrays.asList("Result", text)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}

}
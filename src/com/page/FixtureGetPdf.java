package com.page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class FixtureGetPdf {
	private final List<List<List<String>>> getContent;

	public FixtureGetPdf(String filename, String pageNumber) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		String text = "";
		PdfReader reader;
		try {
			reader = new PdfReader(filename);
			text = PdfTextExtractor.getTextFromPage(reader, Integer.parseInt(pageNumber));
			reader.close();

		} catch (final IOException e) {
			throw new Exception(e);
		}

		getContent.add(Arrays.asList(Arrays.asList("Result", text)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
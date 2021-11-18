package com.page;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class FixtureGetPdfInOneLine {
	private final List<List<List<String>>> getContent;

	public FixtureGetPdfInOneLine(String filename, String pageNumber) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		String text = "";
		PdfReader reader;
		try {
			reader = new PdfReader(filename);
			text = PdfTextExtractor.getTextFromPage(reader, Integer.parseInt(pageNumber));
			text = text.replace("\n", " ");
			text = text.replace("  ", " ");
			reader.close();
			final PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + File.separator + "FitNesseRoot"
					+ File.separator + "files" + File.separator + "temp.txt", "UTF-8");
			writer.println(text);
			writer.close();
		} catch (final IOException e) {
			throw new Exception(e);
		}

		getContent.add(Arrays.asList(Arrays.asList("Result", text)));
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
package com.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlHandle {
	
	public boolean compreTwoXMLFiles(String file1, String file2)
			throws SAXException, IOException, TransformerConfigurationException, ParserConfigurationException {
		Document doc1 = getXmlFile(file1);
		Document doc2 = getXmlFile(file2);
		doc1.normalize();
		doc2.normalize();
		String xmlOutput = convertDocumentToString(doc1);
		String xmlInput = convertDocumentToString(doc2);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setNormalize(true);
		XMLUnit.setIgnoreAttributeOrder(true);
		Diff diff = XMLUnit.compareXML(xmlInput, xmlOutput);
		StringBuffer sb = new StringBuffer();
		diff.appendMessage(sb);
		return diff.identical();
	}
	
	public String diffTwoXMLFiles(String file1, String file2)
			throws SAXException, IOException, TransformerConfigurationException, ParserConfigurationException {
		Document doc1 = getXmlFile(file1);
		Document doc2 = getXmlFile(file2);
		doc1.normalize();
		doc2.normalize();
		String xmlOutput = convertDocumentToString(doc1);
		String xmlInput = convertDocumentToString(doc2);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setNormalize(true);
		XMLUnit.setIgnoreAttributeOrder(true);
		Diff diff = XMLUnit.compareXML(xmlInput, xmlOutput);
		StringBuffer sb = new StringBuffer();
		diff.appendMessage(sb);
		if (diff.identical()) {
			return "PASSED";
		} else {
			return diff.toString();
		}
	}

	public String comparison(String content1, String content2, String elimination)
			throws SAXException, IOException, TransformerConfigurationException {
		Document doc1 = convertStringToDocument(content1);
		Document doc2 = convertStringToDocument(content2);
		doc1.normalize();
		doc2.normalize();
		String xmlOutput = convertDocumentToString(doc1);
		String xmlInput = convertDocumentToString(doc2);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setNormalize(true);
		XMLUnit.setIgnoreAttributeOrder(true);
		Diff diff = XMLUnit.compareXML(xmlInput, xmlOutput);
		StringBuffer sb = new StringBuffer();
		diff.appendMessage(sb);
		if (diff.identical()) {
			return "PASSED";
		} else {
			return "FAILED";
		}
	}

	private Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String convertDocumentToString(Document doc) throws TransformerConfigurationException {
		final TransformerFactory tf = TransformerFactory.newInstance();
		tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			final StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			final String output = writer.getBuffer().toString();
			return output;
		} catch (final TransformerException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Document getXmlFile(String fileName)
			throws SAXException, IOException, ParserConfigurationException, TransformerConfigurationException {
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		return dBuilder.parse(new File(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + fileName));
	}

	public String extractXML(String xmlFile, String tagName) throws XPathExpressionException,
			TransformerConfigurationException, SAXException, IOException, ParserConfigurationException {
		String output = "";
		NodeList nodes = getXmlFile(xmlFile).getElementsByTagName(tagName);
		for (int i = 0; i < nodes.getLength(); i++) {
			output = output + " " + ((Element) nodes.item(i)).getTextContent().trim();
		}
		return output.trim();
	}

	public String extractXML(String xmlFile, String tagName, String index) throws 
			TransformerConfigurationException, SAXException, IOException, ParserConfigurationException {
		NodeList nodes = getXmlFile(xmlFile).getElementsByTagName(tagName);
		return ((Element) nodes.item(Integer.parseInt(index))).getTextContent().trim();			
	}

	public String getFile(String filename) throws IOException {
		@SuppressWarnings("resource")
		final BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("ITS") + File.separator
				+ "FitNesseRoot" + File.separator + "files" + File.separator + filename)));
		String line;
		final StringBuilder sb = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sb.append(line.trim());
		}
		return sb.toString();
	}

	public void toFile(String sContent, String fileName) throws IOException {
		final BufferedWriter writer = new BufferedWriter(new FileWriter(System.getenv("ITS") + File.separator
				+ "FitNesseRoot" + File.separator + "files" + File.separator + fileName));
		writer.write(sContent);
		writer.close();

	}

	public void toXMLFile(String sContent, String fileName) throws IOException, TransformerConfigurationException {
		final BufferedWriter writer = new BufferedWriter(new FileWriter(System.getenv("ITS") + File.separator
				+ "FitNesseRoot" + File.separator + "files" + File.separator + fileName));
		writer.write(convertDocumentToString(convertStringToDocument(sContent)));
		writer.close();

	}

}

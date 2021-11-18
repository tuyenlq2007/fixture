package com.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.RandomStringUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Polling {
	private final List<List<List<String>>> getContent;
	private final List<String> ignoredNode;

	public Polling(String rows, String pollingTime, String timeout) throws Exception {

		HashMap<String, String> processed = getMap(System.getenv("ITS") + File.separator + "lmdb.dat");
		HashMap<String, String> sending = getMap(System.getenv("ITS") + File.separator + "request.dat");

		Integer iTimeout = Integer.parseInt(timeout);
		Integer iPollingTime = Integer.parseInt(pollingTime);
		while (processed.size() < sending.size() && iTimeout > 0) {
			iTimeout = iTimeout - iPollingTime;
			Thread.sleep(iPollingTime);
			processed = getMap(System.getenv("ITS") + File.separator + "lmdb.dat");
			sending = getMap(System.getenv("ITS") + File.separator + "request.dat");
		}
		int row = 0;
		String expectedresult = "";
		String comparisonOfResults = "";
		getContent = new ArrayList<List<List<String>>>();
		ignoredNode = new ArrayList();

		HashMap<String, String> attach = getMap(System.getenv("ITS") + File.separator + "attach.dat");
		HashMap<String, String> expect = getMap(System.getenv("ITS") + File.separator + "expect.dat");
		HashMap<String, String> comparison = getMap(System.getenv("ITS") + File.separator + "comparison.dat");
		HashMap<String, String> elimination = getMap(System.getenv("ITS") + File.separator + "elimination.dat");
		HashMap<String, String> environment = getMap(System.getenv("ITS") + File.separator + "environment.dat");
		HashMap<String, String> hostname = getMap(System.getenv("ITS") + File.separator + "hostname.dat");
		HashMap<String, String> testcaseId = getMap(System.getenv("ITS") + File.separator + "testcaseId.dat");
		HashMap<String, String> attachFile = getMap(System.getenv("ITS") + File.separator + "attachFile.dat");
		HashMap<String, String> expectFile = getMap(System.getenv("ITS") + File.separator + "expectFile.dat");
		HashMap<String, String> starttime = getMap(System.getenv("ITS") + File.separator + "starttime.dat");
		HashMap<String, String> endtime = getMap(System.getenv("ITS") + File.separator + "endtime.dat");
		try {
			Iterator its = sending.entrySet().iterator();
			while (its.hasNext()) {
				Map.Entry pair = (Map.Entry) its.next();
				if (!processed.containsKey(pair.getKey())) {
					row++;
					getContent.add(Arrays.asList(
							Arrays.asList("Environment/Endpoint", getvalue(environment, pair.getKey().toString())),
							Arrays.asList("TestCaseID", getvalue(testcaseId, pair.getKey().toString())),
							Arrays.asList("Request ID", pair.getKey().toString()),
							Arrays.asList("Request Content",
									toNewFile(getvalue(sending, pair.getKey().toString()).toString(),
											getvalue(hostname, pair.getKey().toString()).toString(), "RequestContent")),
							Arrays.asList("Attachment Content",
									getvalue(hostname, pair.getKey().toString()).toString() + File.separator + "files"
											+ File.separator
											+ getvalue(attachFile, pair.getKey().toString()).toString()),
							Arrays.asList("Expected Response",
									getvalue(hostname, pair.getKey().toString()).toString() + File.separator + "files"
											+ File.separator
											+ getvalue(expectFile, pair.getKey().toString()).toString()),
							Arrays.asList("Actual Response", ""), Arrays.asList("Status", "PENDING"),
							Arrays.asList("Response Times (ms)", ""), Arrays.asList("Comparison of Results", "")));
				}
			}

			Iterator it = processed.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				if (sending.containsKey(pair.getKey().toString())) {
					row++;
					try {
						String content1 = getxml(getvalue(expect, pair.getKey().toString()),
								getvalue(comparison, pair.getKey().toString()));
						String content2 = getxml(getvalue(processed, pair.getKey().toString()),
								getvalue(comparison, pair.getKey().toString()));
						expectedresult = comparison(content1, content2,
								getvalue(elimination, pair.getKey().toString()));
						comparisonOfResults = comparisonOfResults(content1, content2,
								getvalue(elimination, pair.getKey().toString()));
					} catch (Exception e) {
						expectedresult = "PROCESSING";
					}
					getContent.add(Arrays.asList(
							Arrays.asList("Environment/Endpoint", getvalue(environment, pair.getKey().toString())),
							Arrays.asList("TestCaseID", getvalue(testcaseId, pair.getKey().toString())),
							Arrays.asList("Request ID", pair.getKey().toString()),
							Arrays.asList("Request Content",
									toNewFile(getvalue(sending, pair.getKey().toString()).toString(),
											getvalue(hostname, pair.getKey().toString()).toString(), "RequestContent")),
							Arrays.asList("Attachment Content",
									getvalue(hostname, pair.getKey().toString()).toString() + File.separator + "files"
											+ File.separator
											+ getvalue(attachFile, pair.getKey().toString()).toString()),
							Arrays.asList("Expected Response",
									getvalue(hostname, pair.getKey().toString()).toString() + File.separator + "files"
											+ File.separator
											+ getvalue(expectFile, pair.getKey().toString()).toString()),
							Arrays.asList("Actual Response",
									toNewFile(
											getxml(getvalue(processed, pair.getKey().toString()).toString(),
													getvalue(comparison, pair.getKey().toString())),
											getvalue(hostname, pair.getKey().toString()).toString(), "ActualResponse")),
							Arrays.asList("Status", expectedresult),
							Arrays.asList("Response Times (ms)", Long.toString(
									Long.parseLong(getvalue(endtime, pair.getKey().toString()).toString()) - Long
											.parseLong(getvalue(starttime, pair.getKey().toString()).toString()))),
							Arrays.asList("Comparison of Results", comparisonOfResults)));
				}
			}

			while (row < Integer.parseInt(rows)) {
				row++;
				getContent.add(Arrays.asList(Arrays.asList("Environment/Endpoint", ""), Arrays.asList("TestCaseID", ""),
						Arrays.asList("Request ID", ""), Arrays.asList("Request Content", ""),
						Arrays.asList("Attachment Content", ""), Arrays.asList("Expected Response", ""),
						Arrays.asList("Actual Response", ""), Arrays.asList("Status", ""),
						Arrays.asList("Response Times (ms)", ""), Arrays.asList("Comparison of Results", "")));
			}
		} catch (Exception e) {

		}

	}

	/*
	 * public static void main(String[] args) throws IOException, JSONException,
	 * SAXException, ParserConfigurationException { String content1 =
	 * getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" +
	 * File.separator + "files" + File.separator +
	 * "request/ExpectNewBusinessQuoteRequestTc001.xml"); String content2 =
	 * getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" +
	 * File.separator + "files" + File.separator +
	 * "request/ExpectNewBusinessQuoteRequestTc001_compare.xml"); content1 =
	 * getxml(content1,"PolicyNewBusinessQuotationProcessResult" ); content2 =
	 * getxml(content2, "PolicyNewBusinessQuotationProcessResult"); String
	 * expectedresult = comparison(content1, content2,
	 * "MessageId; CorrelationId; MessageDateTime; CaseId; ThreadId; PreparedDate; Id; DocumentId; MessageDocumentReferences; ValidUntilDate"
	 * ); String comparisonOfResults = comparisonOfResults(content1, content2,
	 * "MessageId; CorrelationId; MessageDateTime; CaseId; ThreadId; PreparedDate; Id; DocumentId; MessageDocumentReferences; ValidUntilDate"
	 * ); System.out.println(expectedresult);
	 * System.out.println(comparisonOfResults); }
	 */

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

	private String getXmlFile(String fileName) throws SAXException, IOException, ParserConfigurationException {
		new File(fileName);
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		final Document doc = dBuilder.parse(fileName);
		return convertDocumentToString(doc);
	}

	private String toNewFile(String sContent, String hostname, String fileName) throws IOException {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		final String newFileName = File.separator + "files" + File.separator + fileName + "-" + dateFormat.format(date)
				+ RandomStringUtils.randomAlphabetic(10) + ".xml";
		final BufferedWriter writer = new BufferedWriter(
				new FileWriter(System.getenv("ITS") + File.separator + "FitNesseRoot" + newFileName));
		writer.write(sContent);
		writer.close();
		return hostname + newFileName;
	}

	private String getvalue(HashMap<String, String> map, String key) {
		String value = map.get(key);
		return value == null ? "" : value;
	}

	private String getxml(String text, String tag) {
		String content = text.replace("=\r", "").replace("\n", "").replace("=3D", "=");
		try {
			return content.substring(content.indexOf("<" + tag), content.indexOf("</" + tag + ">") + tag.length() + 3);
		} catch (Exception e) {
			return content.substring(content.indexOf("<" + tag + " "),
					content.indexOf("</" + tag + ">") + tag.length() + 3);
		}
	}

	private String comparison(String content1, String content2, String elimination) throws SAXException, IOException {
		Document doc1 = convertStringToDocument(content1);
		Document doc2 = convertStringToDocument(content2);

		for (String ele : elimination.split(";")) {
			removeAll(doc1, Node.ELEMENT_NODE, ele.trim());
			removeAll(doc2, Node.ELEMENT_NODE, ele.trim());
		}
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

	private String comparisonOfResults(String content1, String content2, String elimination)
			throws SAXException, IOException {
		Document doc1 = convertStringToDocument(content1);
		Document doc2 = convertStringToDocument(content2);
		String listIngoredNode = "";
		for (String ele : elimination.split(";")) {
			removeAll2(doc1, Node.ELEMENT_NODE, ele.trim());
			removeAll2(doc2, Node.ELEMENT_NODE, ele.trim());
		}
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
			for (String node : ignoredNode) {
				listIngoredNode = listIngoredNode + node;
			}
			return "Ignored Fields :" + listIngoredNode;
		} else {
			DetailedDiff detailXmlDiff = new DetailedDiff(diff);
			return detailXmlDiff.getAllDifferences().toString();
		}
	}

	private String comparison(String content1, String content2) {
		Document doc1 = convertStringToDocument(content1);
		removeAll(doc1, Node.ELEMENT_NODE, "MessageId");
		removeAll(doc1, Node.ELEMENT_NODE, "CorrelationId");
		removeAll(doc1, Node.ELEMENT_NODE, "MessageDateTime");
		removeAll(doc1, Node.ELEMENT_NODE, "CaseId");
		removeAll(doc1, Node.ELEMENT_NODE, "ThreadId");
		removeAll(doc1, Node.ELEMENT_NODE, "PreparedDate");
		removeAll(doc1, Node.ELEMENT_NODE, "AssignedIdentifier");
		doc1.normalize();

		Document doc2 = convertStringToDocument(content2);
		removeAll(doc2, Node.ELEMENT_NODE, "MessageId");
		removeAll(doc2, Node.ELEMENT_NODE, "CorrelationId");
		removeAll(doc2, Node.ELEMENT_NODE, "MessageDateTime");
		removeAll(doc2, Node.ELEMENT_NODE, "CaseId");
		removeAll(doc2, Node.ELEMENT_NODE, "ThreadId");
		removeAll(doc2, Node.ELEMENT_NODE, "PreparedDate");
		removeAll(doc2, Node.ELEMENT_NODE, "AssignedIdentifier");
		doc2.normalize();
		if (doc1.isEqualNode(doc2)) {
			return "PASSED";
		} else {
			return "FAILED";
		}
	}

	private void removeAll(Node node, short nodeType, String name) {
		if (node.getNodeType() == nodeType && (name == null || node.getNodeName().equals(name))) {
			// ignoredNode.add("<" + node.getNodeName().toString() + ">" +
			// node.getTextContent() + "</" + node.getNodeName().toString() +
			// ">");
			node.getParentNode().removeChild(node);

		} else {
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				removeAll(list.item(i), nodeType, name);
			}
		}
	}

	private void removeAll2(Node node, short nodeType, String name) {
		if (node.getNodeType() == nodeType && (name == null || node.getNodeName().equals(name))) {
			ignoredNode.add("<" + node.getNodeName().toString() + ">" + node.getTextContent() + "</"
					+ node.getNodeName().toString() + ">");
			node.getParentNode().removeChild(node);

		} else {
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				removeAll2(list.item(i), nodeType, name);
			}
		}
	}

	private String convertDocumentToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return null;
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

	private HashMap<String, String> getMap(String db) throws IOException, ClassNotFoundException {
		FileInputStream f = null;
		ObjectInputStream input = null;
		//try {
			final File file = new File(db);
			f = new FileInputStream(file);
			input = new ObjectInputStream(f);
			// Reads the first object in
			final Object readObject = input.readObject();
			input.close();
			return (HashMap<String, String>) readObject;
		/*} finally {
			if (input!=null){
				input.close();
			}
			if (f!=null){
				f.close();	
			}
		}*/
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
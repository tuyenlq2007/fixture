package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.api.PollingDAOImpl;

public class Test5 {
	static List<String> ignoredNode = new ArrayList();

	public static void main(String[] args) throws IOException, SAXException {
		String content1 = getXmlNodeByXpath("C:\\ITS\\TCR0202_Pos02Endor_NewBusinessQuoteResponse.xml",
				"//PolicyNewBusinessQuotationProcessResult/PolicyQuotation/AssignedIdentifier/Id");

		System.out.println(content1);

	}

	public static String getXmlNodeByXpath(String fileName, String xpath) {
		String outcome = "";
		try {
			String newFileName = "C:\\ITS\\TCR0202_Pos02Endor_NewBusinessQuoteResponse.xml";
			File inputFile = new File(newFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = xpath;
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node nNode = nodeList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					outcome = eElement.getTextContent();

				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return outcome;
	}

	private static String getFile(String strXMLFilename) throws IOException {
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

	private static String getvalue(HashMap<String, String> map, String key) {
		String value = map.get(key);
		return value == null ? "" : value;
	}

	private static String getxml(String text, String tag) {
		String content = text.replace("=\r", "").replace("\n", "").replace("=3D", "=");
		try {
			return content.substring(content.indexOf("<" + tag), content.indexOf("</" + tag + ">") + tag.length() + 3);
		} catch (Exception e) {
			return content.substring(content.indexOf("<" + tag + " "),
					content.indexOf("</" + tag + ">") + tag.length() + 3);
		}

	}

	private static String comparison(String content1, String content2, String elimination)
			throws SAXException, IOException {
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

	private static String comparisonOfResults(String content1, String content2, String elimination)
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

	private static void removeAll(Node node, short nodeType, String name) {
		if (node.getNodeType() == nodeType && (name == null || node.getNodeName().equals(name))) {
			// ignoredNode.add("<" + node.getNodeName().toString() + ">" +
			// node.getTextContent() + "</" + node.getNodeName().toString() + ">");
			node.getParentNode().removeChild(node);

		} else {
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				removeAll(list.item(i), nodeType, name);
			}
		}
	}

	private static void removeAll2(Node node, short nodeType, String name) {
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

	private static String convertDocumentToString(Document doc) {
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

	private static Document convertStringToDocument(String xmlStr) {
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

}

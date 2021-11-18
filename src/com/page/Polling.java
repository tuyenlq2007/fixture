package com.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Polling {
	private final List<List<List<String>>> getContent;

	public Polling(String rows) throws Exception {
		int row = 0;
		getContent = new ArrayList<List<List<String>>>();
		HashMap<String, Long> performance = getMap("C:\\ITS\\performance.dat");
				
		Iterator its = performance.entrySet().iterator();
		while (its.hasNext()) {			
			Map.Entry pair = (Map.Entry) its.next();
			if (performance.containsKey(pair.getKey())) {
				row++;
				getContent.add(Arrays.asList(
						Arrays.asList("Number", new Integer(row).toString()),
						Arrays.asList("Test Case", pair.getKey().toString()),
						Arrays.asList("Avg Response", Long.toString(getvalue(performance, pair.getKey().toString()))   )
						));
			}
		}
		
		while (row < Integer.parseInt(rows)) {
			row++;
			getContent.add(Arrays.asList(
					Arrays.asList("Number", new Integer(row).toString()),
					Arrays.asList("Test Case", ""), 
					Arrays.asList("Avg Response", "")));
		}

	}

	private long getvalue(HashMap<String, Long> map, String key) {
		Long value = map.get(key);
		return value == null ? 0 : value;
	}

	/*private String getFile(String file) throws FileNotFoundException {
		return new Scanner(new File(file)).useDelimiter("\\Z").next();
	}*/

	private String getxml(String text, String tag) {
		String content = text.replace("=\r", "").replace("\n", "").replace("=3D", "=");
		return content.substring(content.indexOf("<" + tag), content.indexOf("</" + tag + ">") + tag.length() + 3);

	}

	private String comparison(String content1, String content2, String elimination)
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
		/*
		 * System.out.println(xmlOutput); System.out.println(xmlInput);
		 * System.out.println(diff.identical());
		 */
		if (diff.identical()) {
			return "PASSED";
		} else {
			return "FAILED";
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
			node.getParentNode().removeChild(node);
		} else {
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				removeAll(list.item(i), nodeType, name);
			}
		}
	}

	private String convertDocumentToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			// below code to remove XML declaration
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
			// "yes");
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

	private HashMap<String, Long> getMap(String db) throws IOException, ClassNotFoundException {
		final File file = new File(db);
		FileInputStream f = new FileInputStream(file);
		final ObjectInputStream input = new ObjectInputStream(f);
		// Reads the first object in
		final Object readObject = input.readObject();
		input.close();
		return (HashMap<String, Long>) readObject;
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
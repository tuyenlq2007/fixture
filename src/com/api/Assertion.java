package com.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Assertion {
	private final List<List<List<String>>> getContent;

	public Assertion(String rows) throws Exception {
		int row = 0;
		String expectedresult = "PROCESSING";
		getContent = new ArrayList<List<List<String>>>();
		HashMap<String, String> processed = getMap("C:\\ITS\\lmdb.dat");
		HashMap<String, String> sending = getMap("C:\\ITS\\request.dat");
		HashMap<String, String> attach = getMap("C:\\ITS\\attach.dat");
		HashMap<String, String> expect = getMap("C:\\ITS\\expect.dat");
		HashMap<String, String> comparison = getMap("C:\\ITS\\comparison.dat");
		
		Iterator its = sending.entrySet().iterator();
		while (its.hasNext()) {			
			Map.Entry pair = (Map.Entry) its.next();
			if (!processed.containsKey(pair.getKey())) {
				row++;
				getContent.add(Arrays.asList(
						Arrays.asList("TestCaseID", new Integer(row).toString()),
						Arrays.asList("Request ID", pair.getKey().toString()),
						Arrays.asList("Request Content", getvalue(sending, pair.getKey().toString())),
						Arrays.asList("Attachment Content", getvalue(attach, pair.getKey().toString())),
						Arrays.asList("Expected Response", getvalue(expect, pair.getKey().toString())),
						Arrays.asList("Actual Response", ""),
						Arrays.asList("Status", "PENDING")
						));
			}
		}
		
		Iterator it = processed.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			row++;
			/*try{
			String content1 = getxml(getvalue(expect, pair.getKey().toString()), getvalue(comparison, pair.getKey().toString()));
			String content2 = getxml(getvalue(processed, pair.getKey().toString()), getvalue(comparison, pair.getKey().toString()));
			expectedresult = comparison(content1, content2);
			}
			catch (Exception e){
				expectedresult = "PROCESSING";
			}*/
			getContent.add(Arrays.asList(Arrays.asList("TestCaseID", new Integer(row).toString()),
					Arrays.asList("Request ID", pair.getKey().toString()),
					Arrays.asList("Request Content", getvalue(sending, pair.getKey().toString())),
					Arrays.asList("Attachment Content", getvalue(attach, pair.getKey().toString())),
					Arrays.asList("Expected Response", getvalue(expect, pair.getKey().toString())),
					Arrays.asList("Actual Response", getvalue(processed, pair.getKey().toString())),
					Arrays.asList("Status", expectedresult)));
		}

		while (row < Integer.parseInt(rows)) {
			row++;
			getContent.add(Arrays.asList(Arrays.asList("TestCaseID", new Integer(row).toString()),
					Arrays.asList("Request ID", ""), Arrays.asList("Request Content", ""),
					Arrays.asList("Attachment Content", ""), Arrays.asList("Expected Response", ""),
					Arrays.asList("Actual Response", ""), Arrays.asList("Status", "")));
		}

	}

	private String getvalue(HashMap<String, String> map, String key) {
		String value = map.get(key);
		return value == null ? "" : value;
	}

	private String getFile(String file) throws FileNotFoundException {
		return new Scanner(new File(file)).useDelimiter("\\Z").next();
	}

	private String getxml(String text, String tag) {
		String content = text.replace("=\r", "").replace("\n", "").replace("=3D", "=");
		return content.substring(content.indexOf("<" + tag), content.indexOf("</" + tag + ">") + tag.length() + 3);

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

	private HashMap<String, String> getMap(String db) throws IOException, ClassNotFoundException {
		final File file = new File(db);
		FileInputStream f = new FileInputStream(file);
		final ObjectInputStream input = new ObjectInputStream(f);
		// Reads the first object in
		final Object readObject = input.readObject();
		input.close();
		return (HashMap<String, String>) readObject;
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
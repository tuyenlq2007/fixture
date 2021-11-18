package com.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

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

public class QueryPolling {
	private List<List<List<String>>> getContent;
	private List<String> ignoredNode;

	public QueryPolling(String rows, String timeout, String pollingTime) throws Exception {
		//Thread.sleep(Integer.parseInt(timeout));
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream(System.getenv("ITS") + File.separator + "jdbc.properties");
		// load a properties file
		prop.load(input);
		getContent = new ArrayList<List<List<String>>>();
		ignoredNode = new ArrayList();
		Integer iTimeout = Integer.parseInt(timeout);
		Integer iPollingTime = Integer.parseInt(pollingTime);
		int row = 0;
		getContent = new ArrayList<List<List<String>>>();
		ignoredNode = new ArrayList();
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":"
					+ prop.getProperty("port")
					+ "/demo?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"tle242@csc.com", "root");

		} catch (ClassNotFoundException | SQLException e) {
			connection = null;

			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/*
		 * try { String sql =
		 * "SELECT * FROM Polling WHERE actualresult IS NULL OR actualresult =  '' ";
		 * //String sql = "SELECT * FROM Polling"; pr =
		 * connection.prepareStatement(sql); rstemp = pr.executeQuery();
		 * 
		 * while ((rstemp.next() == true) && (iTimeout > 0)) { rstemp.close();
		 * Thread.sleep(iPollingTime); iTimeout -= iPollingTime; try { rstemp =
		 * pr.executeQuery(); }catch (SQLException e) { e.printStackTrace(); } } try {
		 * rstemp.close(); } catch (SQLException e) { e.printStackTrace(); } } catch
		 * (SQLException e) {
		 * System.out.println("Connection Failed! Check output console");
		 * e.printStackTrace(); } finally { if (pr != null) { try { pr.close(); } catch
		 * (SQLException e) { } } }
		 */
		ResultSet rstemp = null;
		PreparedStatement pr = null;

		String sqltemp = "SELECT * FROM Polling WHERE actualresult IS NULL OR actualresult =  ''  ";
		while (iTimeout > 0) {
			try {
				pr = connection.prepareStatement(sqltemp);
				rstemp = pr.executeQuery();
				if (rstemp.next()) {
					Thread.sleep(iPollingTime);
					iTimeout -= iPollingTime;
				} else {
					iTimeout = 0;
				}
				rstemp.close();

			} catch (SQLException e) {
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
			} finally {
				if (pr != null) {
					try {
						pr.close();
					} catch (SQLException e) {
					}
				}
			}
		}
		
		DataPolling p = null;
		ResultSet rs = null;
		try {
			// String sql = "SELECT * FROM Polling WHERE id = ?";
			String sql = "SELECT * FROM Polling";
			preparedStatement = connection.prepareStatement(sql);
			// preparedStatement.setString(1, id);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Retrieve by column name
				String id = rs.getString("id");
				String request = rs.getString("request");
				String env = rs.getString("enviroment");
				String host = rs.getString("host");
				String testcasename = rs.getString("testcasename");
				String excluding = rs.getString("excluding");
				String body = rs.getString("body");
				String actualresult = rs.getString("actualresult");
				String expectedresult = rs.getString("expectedresult");
				String comparision = rs.getString("comparision");
				String status = rs.getString("status");
				Date starttime = null;
				Date endtime = null;
				if (rs.getDate("starttime") != null) {
					java.sql.Timestamp dbSqlTimestamp = rs.getTimestamp("starttime");
					starttime = new java.util.Date(dbSqlTimestamp.getTime());
				}
				if (rs.getDate("endtime") != null) {
					java.sql.Timestamp dbSqlTimestamp = rs.getTimestamp("endtime");
					endtime = new java.util.Date(dbSqlTimestamp.getTime());
				}

				if (actualresult.isEmpty() || actualresult.trim() == "") {
					row++;
					getContent.add(Arrays.asList(Arrays.asList("Environment/Endpoint", env),
							Arrays.asList("TestCaseID", testcasename), Arrays.asList("Request ID", id),
							Arrays.asList("Request Content", toNewFile(request, host, "RequestContent")),
							Arrays.asList("Attachment Content",
									host + File.separator + "files" + File.separator + body),
							Arrays.asList("Expected Response",
									host + File.separator + "files" + File.separator + expectedresult),
							Arrays.asList("Actual Response", ""), Arrays.asList("Status", "PENDING"),
							Arrays.asList("Response Times (ms)", ""), Arrays.asList("Comparison of Results", "")));
				}

				if (!actualresult.isEmpty() && actualresult.trim() != "") {
					String content1 = getxml(getFile(System.getenv("ITS") + File.separator + "FitNesseRoot"
							+ File.separator + "files" + File.separator + expectedresult), comparision);
					String content2 = getxml(actualresult, comparision);
					// content1 = content2.replaceAll("&", "&amp;");
					// content2 = content2.replaceAll("&", "&amp;");
					String theExpectedresult = comparison(content1, content2, excluding);
					String comparisonOfResults = comparisonOfResults(content1, content2, excluding);

					row++;
					getContent.add(Arrays.asList(Arrays.asList("Environment/Endpoint", env),
							Arrays.asList("TestCaseID", testcasename), Arrays.asList("Request ID", id),
							Arrays.asList("Request Content", toNewFile(request, host, "RequestContent")),
							Arrays.asList("Attachment Content",
									host + File.separator + "files" + File.separator + body),
							Arrays.asList("Expected Response",
									host + File.separator + "files" + File.separator + expectedresult),
							Arrays.asList("Actual Response", toNewFile(actualresult, host, "ActualResponse")),
							Arrays.asList("Status", theExpectedresult),
							Arrays.asList("Response Times (ms)", comparision),
							Arrays.asList("Comparison of Results", comparisonOfResults)));

				}
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();

		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
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
		// try {
		final File file = new File(db);
		f = new FileInputStream(file);
		input = new ObjectInputStream(f);
		// Reads the first object in
		final Object readObject = input.readObject();
		input.close();
		return (HashMap<String, String>) readObject;
		/*
		 * } finally { if (input!=null){ input.close(); } if (f!=null){ f.close(); } }
		 */
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
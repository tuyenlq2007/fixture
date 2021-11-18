package com.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Transporter {

	public String PollingTimeoutRetry(String requests, String timeout, String retry) {

		return "";
	}

	public boolean cleanLog() throws FileNotFoundException, InterruptedException {
		int counter = 10;
		boolean bExit = false;
		while (!bExit && counter > 0) {
			try {
				final PrintWriter writer = new PrintWriter(new File(System.getenv("ITS") + File.separator + "log.txt"));
				final PrintWriter writer1 = new PrintWriter(new File(System.getenv("ITS") + File.separator
						+ "FitNesseRoot" + File.separator + "files" + File.separator + "transporter.log"));
				writer.print("");
				writer1.print("");
				writer1.close();
				writer.close();
				bExit = true;
			} catch (Exception e) {
				Thread.sleep(1000);
				counter--;
			}
		}

		return true;

	}

	private String convertDocumentToString(Document doc) {
		final TransformerFactory tf = TransformerFactory.newInstance();
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

	private void copyFile(String source, String dest, Boolean overwrite) throws IOException, InterruptedException {
		int counter = 10;
		boolean bExit = false;
		while (!bExit && counter > 0) {
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(source);
				os = new FileOutputStream(dest);
				final byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				bExit = true;
			} catch (Exception e) {
				Thread.sleep(1000);
				counter--;
			} finally {
				is.close();
				os.close();
			}
		}

	}

	public List doTable(List<List<String>> table) throws IOException, InterruptedException {
		return ListUtility.list();

	}

	public String getAttribute(String root, String tag) throws IOException {
		return root.substring(root.indexOf("<" + tag + ">") + tag.length() + 2, root.indexOf("</" + tag + ">"));
	}

	public String getAttributeFromLog(String tag) throws IOException {
		final String str = getFile(System.getenv("ITS") + File.separator + "log.txt");
		return getAttribute(str, tag);
	}

	public String getFile(String strXMLFilename) throws IOException {
		@SuppressWarnings("resource")
		final BufferedReader br = new BufferedReader(new FileReader(new File(strXMLFilename)));
		String line;
		final StringBuilder sb = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sb.append(line.trim());
		}
		return sb.toString();
	}

	public String getServiceLog() throws IOException {
		return new String(Files.readAllBytes(Paths.get(System.getenv("ITS") + File.separator + "log.txt")));
	}

	public String getServiceLog(String fromUrl) throws IOException {
		final URL website = new URL(fromUrl);
		final ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		final FileOutputStream fos = new FileOutputStream(System.getenv("ITS") + File.separator + "log.txt");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		return "";
	}

	public String getXmlFile(String fileName) throws SAXException, IOException, ParserConfigurationException {
		new File(fileName);
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		final Document doc = dBuilder.parse(fileName);
		return convertDocumentToString(doc);
	}

	public String IGetRandom() {
		UUID uniqueKey = UUID.randomUUID();
		return uniqueKey.toString();
	}

	public String IGetRandomNumber(final String value) {
		final char[] chars = "0123456789".toCharArray();
		final StringBuilder sb = new StringBuilder();
		final Random random = new Random();
		for (int i = 0; i < Integer.parseInt(value); i++) {
			final char c = chars[random.nextInt(chars.length)];
			sb.append(Character.toLowerCase(c));
		}
		final String output = sb.toString();
		return output;
	}

	public boolean pause(final String timeout) throws NumberFormatException, InterruptedException {
		Thread.sleep(Integer.parseInt(timeout));
		return true;
	}

	public String replaceXmlFileAttribute(String fileName, String oldAttribute, String newAttribute)
			throws SAXException, IOException, ParserConfigurationException, InterruptedException {

		String newFileName = System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + fileName;

		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		final Document doc = dBuilder.parse(newFileName);
		final String updatedcontent = convertDocumentToString(doc).replaceAll(
				oldAttribute.replace("<script>", "").replace("</script>", "").trim(),
				newAttribute.replace("<script>", "").replace("</script>", "").trim());
		updateFile(updatedcontent, newFileName);
		Thread.sleep(3000);
		return updatedcontent;
	}

	public String replaceXmlFileAttribute(String fileName, String tag, String oldValue, String newValue)
			throws SAXException, IOException, ParserConfigurationException, InterruptedException {
		String newFileName = System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + fileName;

		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		final Document doc = dBuilder.parse(newFileName);
		final String updatedcontent = convertDocumentToString(doc);
		final StringBuffer sb = new StringBuffer();
		final String pattern1 = "<" + tag + ">";
		final String pattern2 = "</" + tag + ">";

		final Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
		final Matcher m = p.matcher(updatedcontent);
		while (m.find()) {
			m.appendReplacement(sb, pattern1 + newValue + pattern2);
		}
		m.appendTail(sb);
		updateFile(sb.toString(), newFileName);
		Thread.sleep(3000);
		return sb.toString();
	}

	/*
	 * public boolean sendRequest(final String fileName) throws
	 * NumberFormatException, InterruptedException, IOException {
	 * copyFile(fileName, System.getenv("ITS") +
	 * "/FitNesseRoot/files/request/request.xml", Boolean.FALSE);
	 * Runtime.getRuntime().exec(
	 * "cmd /c start cmd.exe /K \"cd C:/ITS/FitNesseRoot/files/request  & mvn -X com.smartbear.soapui:soapui-maven-plugin:test"
	 * ); Runtime.getRuntime().exec("taskkill /f /im cmd.exe"); return true; }
	 * 
	 * public boolean sendRequest(final String fileName, final String requestId)
	 * throws NumberFormatException, InterruptedException, IOException,
	 * ClassNotFoundException, SAXException, ParserConfigurationException {
	 * copyFile(fileName, "C:/ITS/FitNesseRoot/files/request/request.xml",
	 * Boolean.FALSE); Runtime.getRuntime().exec(
	 * "cmd /c start cmd.exe /K \"cd C:/ITS/FitNesseRoot/files/request  & mvn -X com.smartbear.soapui:soapui-maven-plugin:test"
	 * ); Runtime.getRuntime().exec("taskkill /f /im cmd.exe"); //
	 * putMap(requestId, "SENDING"); return true; }
	 * 
	 * public boolean sendRequest(final String requestFile, final String attach,
	 * final String requestId, final String expect, final String comparison)
	 * throws NumberFormatException, InterruptedException, IOException,
	 * ClassNotFoundException, SAXException, ParserConfigurationException {
	 * copyFile(requestFile, System.getenv("ITS") + File.separator +
	 * "FitNesseRoot/files/request/request.xml", Boolean.FALSE);
	 * Runtime.getRuntime().exec(
	 * "cmd /c start cmd.exe /K \"cd C:/ITS/FitNesseRoot/files/request  & mvn -X com.smartbear.soapui:soapui-maven-plugin:test"
	 * ); Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
	 * putMap(requestId, requestFile, System.getenv("ITS") + File.separator +
	 * "request.dat"); putMap(requestId, attach, System.getenv("ITS") +
	 * File.separator + "attach.dat"); putMap(requestId, expect,
	 * System.getenv("ITS") + File.separator + "expect.dat"); putMap(requestId,
	 * comparison, System.getenv("ITS") + File.separator + "comparison.dat");
	 * return true; }
	 * 
	 * public boolean sendRequest(final String requestFile, final String attach,
	 * final String requestId, final String expect, final String comparison,
	 * final String elimination) throws NumberFormatException,
	 * InterruptedException, IOException, ClassNotFoundException, SAXException,
	 * ParserConfigurationException { copyFile(requestFile, System.getenv("ITS")
	 * + "/FitNesseRoot/files/request/request.xml", Boolean.FALSE);
	 * Runtime.getRuntime().exec(
	 * "cmd /c start cmd.exe /K \"cd C:/ITS/FitNesseRoot/files/request  & mvn -X com.smartbear.soapui:soapui-maven-plugin:test"
	 * ); Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
	 * putMap(requestId, requestFile, System.getenv("ITS") + File.separator +
	 * "request.dat"); putMap(requestId, attach, System.getenv("ITS") +
	 * File.separator + "attach.dat"); putMap(requestId, expect,
	 * System.getenv("ITS") + File.separator + "expect.dat"); putMap(requestId,
	 * comparison, System.getenv("ITS") + File.separator + "comparison.dat");
	 * putMap(requestId, elimination, System.getenv("ITS") + File.separator +
	 * "elimination.dat"); return true; }
	 * 
	 * public boolean send(final String requestFile, final String attach, final
	 * String requestId, final String expect, final String comparison, final
	 * String elimination) throws NumberFormatException, InterruptedException,
	 * IOException, ClassNotFoundException, SAXException,
	 * ParserConfigurationException { copyFile(requestFile, System.getenv("ITS")
	 * + "/FitNesseRoot/files/request/request.xml", Boolean.FALSE);
	 * Runtime.getRuntime().exec(
	 * "cmd /c start cmd.exe /K \"cd C:/ITS/FitNesseRoot/files/request  & mvn -X com.smartbear.soapui:soapui-maven-plugin:test"
	 * ); Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
	 * putMap(requestId, getFile(System.getenv("ITS") +
	 * "/FitNesseRoot/files/request/request.xml"), "C:/ITS/request.dat");
	 * putMap(requestId, attach, System.getenv("ITS") + "/attach.dat");
	 * putMap(requestId, expect, System.getenv("ITS") + "/expect.dat");
	 * putMap(requestId, comparison, System.getenv("ITS") + "/comparison.dat");
	 * putMap(requestId, elimination, System.getenv("ITS") +
	 * "/elimination.dat"); return true; }
	 */
	public boolean sendRequest(String environment, String hostname, String testcaseId, final String requestFile,
			final String attach, final String requestId, final String expect, final String comparison,
			final String elimination) throws NumberFormatException, InterruptedException, IOException,
			ClassNotFoundException, SAXException, ParserConfigurationException {
		copyFile(
				System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files" + File.separator
						+ attach,
				System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files" + File.separator
						+ "request" + File.separator + "attach.xml",
				Boolean.FALSE);
		Runtime.getRuntime().exec(
				"cmd /c start cmd.exe /K \"cd C:/ITS/FitNesseRoot/files/request  & mvn -X com.smartbear.soapui:soapui-maven-plugin:test");
		Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		putMap(requestId, environment, System.getenv("ITS") + File.separator + "environment.dat");
		putMap(requestId, hostname, System.getenv("ITS") + File.separator + "hostname.dat");
		putMap(requestId, testcaseId, System.getenv("ITS") + File.separator + "testcaseId.dat");
		putMap(requestId,
				getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
						+ File.separator + "request" + File.separator + "request.xml"),
				System.getenv("ITS") + File.separator + "request.dat");
		putMap(requestId, getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + attach), System.getenv("ITS") + File.separator + "attach.dat");
		putMap(requestId, getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + expect), System.getenv("ITS") + File.separator + "expect.dat");
		putMap(requestId, attach, System.getenv("ITS") + File.separator + "attachFile.dat");
		putMap(requestId, expect, System.getenv("ITS") + File.separator + "expectFile.dat");
		putMap(requestId, comparison, System.getenv("ITS") + File.separator + "comparison.dat");
		putMap(requestId, elimination, System.getenv("ITS") + File.separator + "elimination.dat");
		putMap(requestId, Long.toString(System.currentTimeMillis()),
				System.getenv("ITS") + File.separator + "starttime.dat");
		log("RequestId:" + requestId + ";StartTime:" + Long.toString(System.currentTimeMillis()));
		log("RequestId:" + requestId + ";testcaseId:" + testcaseId);
		return true;
	}

	public boolean send(String environment, String hostname, String testcaseId, final String requestFile,
			final String attach, final String requestId, final String expect, final String comparison,
			final String elimination) throws NumberFormatException, InterruptedException, IOException,
			ClassNotFoundException, SAXException, ParserConfigurationException {
		copyFile(
				System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files" + File.separator
						+ requestFile,
				System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files" + File.separator
						+ "request" + File.separator + "request.xml",
				Boolean.FALSE);

		Runtime.getRuntime().exec(
				"cmd /c start cmd.exe /K \"cd C:/ITS/FitNesseRoot/files/request  & mvn -X com.smartbear.soapui:soapui-maven-plugin:test");
		Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		putMap(requestId, environment, System.getenv("ITS") + File.separator + "environment.dat");
		putMap(requestId, hostname, System.getenv("ITS") + File.separator + "hostname.dat");
		putMap(requestId, testcaseId, System.getenv("ITS") + File.separator + "testcaseId.dat");
		putMap(requestId,
				getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
						+ File.separator + "request" + File.separator + "request.xml"),
				System.getenv("ITS") + File.separator + "request.dat");
		putMap(requestId, getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + attach), System.getenv("ITS") + File.separator + "attach.dat");
		putMap(requestId, getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + expect), System.getenv("ITS") + File.separator + "expect.dat");
		putMap(requestId, attach, System.getenv("ITS") + File.separator + "attachFile.dat");
		putMap(requestId, expect, System.getenv("ITS") + File.separator + "expectFile.dat");
		putMap(requestId, comparison, System.getenv("ITS") + File.separator + "comparison.dat");
		putMap(requestId, elimination, System.getenv("ITS") + File.separator + "elimination.dat");
		putMap(requestId, Long.toString(System.currentTimeMillis()),
				System.getenv("ITS") + File.separator + "starttime.dat");
		log("RequestId:" + requestId + ";StartTime:" + Long.toString(System.currentTimeMillis()));
		log("RequestId:" + requestId + ";testcaseId:" + testcaseId);
		return true;
	}

	public boolean sendInbound(String environment, String hostname, String testcaseId, final String requestFile,
			final String attach, final String requestId, final String expect, final String comparison,
			final String elimination) throws NumberFormatException, InterruptedException, IOException,
			ClassNotFoundException, SAXException, ParserConfigurationException {
		/*
		 * copyFile( System.getenv("ITS") + File.separator + "FitNesseRoot" +
		 * File.separator + "files" + File.separator + requestFile,
		 * System.getenv("ITS") + File.separator + "FitNesseRoot" +
		 * File.separator + "files" + File.separator + "request" +
		 * File.separator + "request.xml", Boolean.FALSE);
		 * 
		 * Runtime.getRuntime().exec(
		 * "cmd /c start cmd.exe /K \"cd C:/ITS/FitNesseRoot/files/request  & mvn -X com.smartbear.soapui:soapui-maven-plugin:test"
		 * ); Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		 */

		// String soapEndpointUrl =
		// "http://dxc-b2b-its-226484134.ap-southeast-2.elb.amazonaws.com/link/InboxEndpoint";
		String soapAction = "http://www.ACORD.org/Standards/AcordMsgSvc/Inbox#PostRq";
		// callSoapWebService(soapEndpointUrl, soapAction,
		// "request/SoapXmlRequest.xml", "request/QuoteRequestTc001.xml");
		callSoapWebService(environment, soapAction, requestFile, attach);
		putMap(requestId, environment, System.getenv("ITS") + File.separator + "environment.dat");
		putMap(requestId, hostname, System.getenv("ITS") + File.separator + "hostname.dat");
		putMap(requestId, testcaseId, System.getenv("ITS") + File.separator + "testcaseId.dat");
		putMap(requestId,
				getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
						+ File.separator + "request" + File.separator + "request.xml"),
				System.getenv("ITS") + File.separator + "request.dat");
		putMap(requestId, getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + attach), System.getenv("ITS") + File.separator + "attach.dat");
		putMap(requestId, getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + expect), System.getenv("ITS") + File.separator + "expect.dat");
		putMap(requestId, attach, System.getenv("ITS") + File.separator + "attachFile.dat");
		putMap(requestId, expect, System.getenv("ITS") + File.separator + "expectFile.dat");
		putMap(requestId, comparison, System.getenv("ITS") + File.separator + "comparison.dat");
		putMap(requestId, elimination, System.getenv("ITS") + File.separator + "elimination.dat");
		putMap(requestId, Long.toString(System.currentTimeMillis()),
				System.getenv("ITS") + File.separator + "starttime.dat");
		log("RequestId:" + requestId + ";StartTime:" + Long.toString(System.currentTimeMillis()));
		log("RequestId:" + requestId + ";testcaseId:" + testcaseId);
		return true;
	}

	
	public String sendToInbound(String environment, String hostname, String testcaseId, final String requestFile,
			final String attach, final String requestId, final String expect, final String comparison,
			final String elimination) throws NumberFormatException, InterruptedException, IOException,
			ClassNotFoundException, SAXException, ParserConfigurationException {
		String soapAction = "http://www.ACORD.org/Standards/AcordMsgSvc/Inbox#PostRq";
			
		new PollingDAOImpl().createPolling(new DataPolling(requestId, environment,hostname,testcaseId,elimination,getFile(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + requestFile), attach, "", expect, comparison, "PENDING", new Date(), new Date()));	
		
		return callSoapWebService(environment, soapAction, requestFile, attach);
	}
	
	
	public void updatePollingActualResult(String id, String actualResult) throws ClassNotFoundException, IOException, InterruptedException{
		new PollingDAOImpl().updatePollingActualResult(id, actualResult);
	}
	public void setPerformanceTimeoutRetry(String timeout, String retry)
			throws ClassNotFoundException, InterruptedException {
		log("timeout:" + timeout + ";retry:" + retry);
	}

	private void log(String line) throws ClassNotFoundException, InterruptedException {
		int counter = 100;
		boolean bExit = false;
		while (!bExit && counter > 0) {
			try {
				/*Files.write(Paths.get(System.getenv("ITS") + File.separator + "log.txt"),
						System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
				Files.write(Paths.get(System.getenv("ITS") + File.separator + "log.txt"), line.getBytes(),
						StandardOpenOption.APPEND);*/
				Files.write(
						Paths.get(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
								+ File.separator + "transporter.log"),
						System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
				Files.write(Paths.get(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
						+ File.separator + "transporter.log"), line.getBytes(), StandardOpenOption.APPEND);
				bExit = true;
			} catch (Exception e) {
				Thread.sleep(300);
				counter--;
			}
		}
	}

	public boolean cleanDatabase() throws IOException, ClassNotFoundException, InterruptedException {
		cleardata(System.getenv("ITS") + File.separator + "environment.dat");
		cleardata(System.getenv("ITS") + File.separator + "hostname.dat");
		cleardata(System.getenv("ITS") + File.separator + "testcaseId.dat");
		cleardata(System.getenv("ITS") + File.separator + "request.dat");
		cleardata(System.getenv("ITS") + File.separator + "attach.dat");
		cleardata(System.getenv("ITS") + File.separator + "attachFile.dat");
		cleardata(System.getenv("ITS") + File.separator + "expect.dat");
		cleardata(System.getenv("ITS") + File.separator + "expectFile.dat");
		cleardata(System.getenv("ITS") + File.separator + "performance.dat");
		cleardata(System.getenv("ITS") + File.separator + "comparison.dat");
		cleardata(System.getenv("ITS") + File.separator + "lmdb.dat");
		cleardata(System.getenv("ITS") + File.separator + "elimination.dat");
		cleardata(System.getenv("ITS") + File.separator + "starttime.dat");
		cleardata(System.getenv("ITS") + File.separator + "endtime.dat");
		new PollingDAOImpl().dropDatabase();
		return true;
	}

	public boolean startTransporterWithListeningPortTargetHostTargetPort(String listeningPort, String targetHost,
			String targetPort) throws IOException {
		Runtime.getRuntime().exec("cmd /c start java -jar C:\\ITS\\intermediary.jar " + listeningPort + " " + targetHost
				+ " " + targetPort);
		return true;

	}

	public boolean stopTransporterAtPort(String listeningPort) throws IOException, InterruptedException {
		String pid = "";
		final Process p = Runtime.getRuntime().exec("cmd.exe /c netstat -ano ");
		final Scanner sc = new Scanner(p.getInputStream(), "IBM850");
		while (sc.hasNextLine()) {
			final String line = sc.nextLine();
			if (line.toLowerCase().contains(listeningPort)) {
				pid = line.substring(line.lastIndexOf(" ")).trim();
			}
		}
		sc.close();
		Runtime.getRuntime().exec("taskkill /F /PID " + pid);
		Runtime.getRuntime().exec("taskkill /f /im cmd.exe");

		return true;

	}

	public boolean toFile(String sContent) throws IOException, InterruptedException {
		int counter = 10;
		boolean bExit = false;
		while (!bExit && counter > 0) {
			try {
				final BufferedWriter writer = new BufferedWriter(
						new FileWriter(System.getenv("ITS") + File.separator + "log.txt"));
				writer.write(sContent);
				writer.close();
				bExit = true;
			} catch (Exception e) {
				Thread.sleep(1000);
				counter--;
			}
		}

		return true;
	}

	private boolean updateFile(String strData, String strXMLFilename) throws IOException, InterruptedException {
		int counter = 10;
		boolean bExit = false;
		while (!bExit && counter > 0) {
			try {
				strData.replace("<script>", "").replace("</script>", "").trim();
				final BufferedWriter writer = new BufferedWriter(new FileWriter(strXMLFilename));
				writer.write(strData);
				writer.close();
				bExit = true;
			} catch (Exception e) {
				Thread.sleep(1000);
				counter--;
			}
		}

		return true;
	}

	private void putMap(String key, String value, String database)
			throws IOException, ClassNotFoundException, InterruptedException {
		boolean bExit = false;
		ObjectInputStream input = null;
		FileInputStream f = null;
		File file = null;
		HashMap<String, String> map = new HashMap<String, String>();
		while (!bExit) {
			try {
				
				file = new File(database);
				f = new FileInputStream(file);
				/*java.nio.channels.FileLock lock = f.getChannel().lock();
				try{	*/		
				input = new ObjectInputStream(f);
				// Reads the first object in
				final Object readObject = input.readObject();	
				map = (HashMap<String, String>) readObject;
				map.put(key, value);
				bExit = true;
			/*	}finally{
					lock.release();
				}*/
			} catch (Exception e) {
				Thread.sleep(1000);
			} finally {
				if (input!=null){
					input.close();
				}
				if (f!=null){
					f.close();	
				}
				
			}
		}
		if (bExit)
			writeFile(database, map);
	}

	private void writeFile(String database, HashMap<String, String> map)
			throws IOException, ClassNotFoundException, InterruptedException {
		boolean bExit = false;
		ObjectOutputStream s = null;
		FileOutputStream f2 = null;
		File file = null;
		while (!bExit) {
			try {
				file = new File(database);
				f2 = new FileOutputStream(file);
				/*java.nio.channels.FileLock lock = f2.getChannel().lock();
				try{*/
				s = new ObjectOutputStream(f2);
				s.writeObject(map);
				bExit = true;
				/*}finally{
					lock.release();
				}*/
			} catch (Exception e) {
				Thread.sleep(1000);
			} finally {
				if (s!=null){
					s.close();
				}
				if (f2!=null){
					f2.close();	
				}
			}
		}

	}

	private boolean cleardata(String dbName) throws IOException, ClassNotFoundException, InterruptedException {
		int counter = 10;
		boolean bExit = false;
		while (!bExit && counter > 0) {
			try {
				File file = new File(dbName);
				FileInputStream f = new FileInputStream(file);
				ObjectInputStream input = new ObjectInputStream(f);
				// Reads the first object in
				Object readObject = input.readObject();
				input.close();
				f.close();
				HashMap<String, String> map = new HashMap<String, String>();
				try {
					map = (HashMap<String, String>) readObject;
				} catch (Exception e) {
				}

				map.clear();
				{
					FileOutputStream f2 = new FileOutputStream(file);
					ObjectOutputStream s = new ObjectOutputStream(f2);
					s.writeObject(map);
					s.close();
					f2.close();
				}
				bExit = true;
			} catch (Exception e) {
				Thread.sleep(1000);
				counter--;
			}
		}

		return true;
	}

	public String getTag(String root, String tag) {
		try {
			return root.substring(root.indexOf("<" + tag + ">") + tag.length() + 2, root.indexOf("</" + tag + ">"));
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			return "";
		}

	}

	public String replaceXmlNodeByXpath(String fileName, String xpath, String currentValue, String newValue)
			throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError,
			InterruptedException {
		int counter = 10;
		String outcome = "";
		boolean bExit = false;
		while (!bExit && counter > 0) {
			try {

				try {
					String newFileName = System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator
							+ "files" + File.separator + fileName;
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
							if (outcome.equalsIgnoreCase(currentValue)) {
								nodeList.item(i).setTextContent(newValue);
							}
						}
					}
					TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc),
							new StreamResult(new File(newFileName)));
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				}
				bExit = true;
			} catch (Exception e) {
				Thread.sleep(1000);
				counter--;
			}
		}

		return outcome;
	}

	public String replaceXmlNodeByXpath(String fileName, String xpath, String newValue)
			throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError,
			InterruptedException {
		int counter = 10;
		String outcome = "";
		boolean bExit = false;
		while (!bExit && counter > 0) {
			try {

				try {
					String newFileName = System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator
							+ "files" + File.separator + fileName;
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
								nodeList.item(i).setTextContent(newValue);
						}
					}
					TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc),
							new StreamResult(new File(newFileName)));
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				}
				bExit = true;
			} catch (Exception e) {
				Thread.sleep(1000);
				counter--;
			}
		}

		return outcome;
	}
	public String getXmlNodeByXpath(String fileName, String xpath) {
		String outcome = "";
		try {
			String newFileName = System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
					+ File.separator + fileName;
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

	public String getXmlNodeByIdAndXpath(String id, String xpath) throws ClassNotFoundException, IOException, ParserConfigurationException, XPathExpressionException, SAXException {
		String outcome = "";
			/*HashMap<String, String> processed = getMap(System.getenv("ITS") + File.separator + "lmdb.dat");
			HashMap<String, String> comparison = getMap(System.getenv("ITS") + File.separator + "comparison.dat");*/
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream(System.getenv("ITS") + File.separator + "jdbc.properties");
			// load a properties file
			prop.load(input);	
			try {
				// Class.forName("com.mysql.jdbc.Driver");
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":"
						+ prop.getProperty("port") + "/demo?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tle242@csc.com",
						"root");

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

			DataPolling p = null;
			ResultSet rs = null;
			try {
				String sql = "SELECT * FROM Polling WHERE id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, id);

				rs = preparedStatement.executeQuery();

				while (rs.next()) {
					// Retrieve by column name
					//String id = rs.getString("id");
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

					if (!actualresult.isEmpty()  && actualresult.trim() != "") {	
						Document doc = dBuilder.parse(new InputSource(new StringReader(
								getxml(actualresult.toString(), comparision.toString()))));

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
					} 
				}
			}catch (SQLException e) {
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
		return outcome;
	}

	public String getXmlNodeByIdAndXpath(String comparison, String id, String xpath) throws ClassNotFoundException, IOException, ParserConfigurationException, XPathExpressionException, SAXException {
		String outcome = "";
			/*HashMap<String, String> processed = getMap(System.getenv("ITS") + File.separator + "lmdb.dat");
			HashMap<String, String> comparison = getMap(System.getenv("ITS") + File.separator + "comparison.dat");*/
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream(System.getenv("ITS") + File.separator + "jdbc.properties");
			// load a properties file
			prop.load(input);	
			try {
				// Class.forName("com.mysql.jdbc.Driver");
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":"
						+ prop.getProperty("port") + "/demo?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tle242@csc.com",
						"root");

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

			DataPolling p = null;
			ResultSet rs = null;
			try {
				String sql = "SELECT * FROM Polling WHERE id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, id);

				rs = preparedStatement.executeQuery();

				while (rs.next()) {
					// Retrieve by column name
					//String id = rs.getString("id");
					String request = rs.getString("request");
					String env = rs.getString("enviroment");
					String host = rs.getString("host");
					String testcasename = rs.getString("testcasename");
					String excluding = rs.getString("excluding");
					String body = rs.getString("body");
					String actualresult = rs.getString("actualresult");
					String expectedresult = rs.getString("expectedresult");
					//String comparision = rs.getString("comparision");
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

					if (!actualresult.isEmpty()  && actualresult.trim() != "") {	
						Document doc = dBuilder.parse(new InputSource(new StringReader(
								getxml(actualresult.toString(), comparison))));

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
					} 
				}
			}catch (SQLException e) {
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
		return outcome;
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

	private String getvalue(HashMap<String, String> map, String key) {
		String value = map.get(key);
		return value == null ? "" : value;
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

	private String callSoapWebService(String soapEndpointUrl, String soapAction, String fileRequest,
			String fileAttached) {
		String responseMessage = "";
		try {
			// 1. Create SOAP connection object through factories.
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// 5. Send SOAP Message to SOAP Server
			/*
			 * SOAPMessage soapResponse = soapConnection
			 * .call(createSOAPRequest(soapAction,
			 * "C:\\ITS\\FitNesseRoot\\files\\request\\SoapXmlRequest.xml",
			 * "C:\\ITS\\FitNesseRoot\\files\\request\\QuoteRequestTc001.xml"),
			 * soapEndpointUrl); soapResponse.writeTo(System.out);
			 */

			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction,
					System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files" + File.separator
							+ fileRequest,
					System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files" + File.separator
							+ fileAttached),
					soapEndpointUrl);

			soapResponse.writeTo(System.out);
			responseMessage = soapResponse.getSOAPBody().getTextContent();

			// 6. Close connection
			soapConnection.close();
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
		return responseMessage;
	}

	private SOAPMessage createSOAPRequest(String soapAction, String fileRequest, String... multiFiles)
			throws Exception {
		// 1. Create SOAP message object through factories.
		// MessageFactory messageFactory = MessageFactory.newInstance();
		// SOAPMessage soapMessage = messageFactory.createMessage();
		// 2. Retrieve the SOAP part and envelope
		// createSoapEnvelope(soapMessage);
		SOAPMessage soapMessage = readSoapMessage(fileRequest);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapAction);

		// 3. Attachment a file XML
		for (String s : multiFiles) {
			File file = new File(s); // ("D:/07.projects/07.04other/QuoteRequestTc001.xml");
			DataHandler dh = new DataHandler(new FileDataSource(file));
			AttachmentPart attachment = soapMessage.createAttachmentPart(dh);
			String contentType = Files.probeContentType(file.toPath());
			attachment.setContentType(contentType);
			attachment.setContentId("6d06eeca-7be7-4d5b-b0ca-acb934278655");

			// Add attachment
			soapMessage.addAttachmentPart(attachment);
		}
		// 4. Save ALL change
		soapMessage.saveChanges();

		/* Print the request message, just for debugging purposes */
		// System.out.println("Request SOAP Message:");
		// System.out.println(contentType);
		// soapMessage.writeTo(System.out);
		// System.out.println("\n");

		return soapMessage;
	}

	/**
	 * Converts the given XML namespace to XML document. If the conversion
	 * fails, null is returned.
	 *
	 * @param xml
	 *            path string to be converted
	 * @return XML document
	 */
	private Document xmlStrToDoc(String xmlFile) {
		try {
			File inputFile = new File(xmlFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private SOAPMessage readSoapMessage(String filename) throws SOAPException, FileNotFoundException {
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		soapPart.setContent(new StreamSource(new FileInputStream(filename)));
		message.saveChanges();
		return message;
	}

}

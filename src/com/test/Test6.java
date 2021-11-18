package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import performance.ui.RunnableDemo;

public class Test6 {

	public static void main(String[] args) throws IOException {
		/*String url = "http://20.203.6.211/FrontPage.OmniChannel.WebTest.TestCasePerformance1?test";
		HttpURLConnection con = null;

		try {

			URL myurl = new URL(url);
			con = (HttpURLConnection) myurl.openConnection();

			con.setRequestMethod("GET");

			StringBuilder content;

			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

				String line;
				content = new StringBuilder();

				while ((line = in.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
			}

			System.out.println(content.toString());

		} finally {

			con.disconnect();
		}*/
		//RunnableDemo object = new RunnableDemo(); 
        //object.start(); 
	}

	/*
	 * The example below requests from the Web Service at:
	 * http://www.webservicex.net/uszip.asmx?op=GetInfoByCity
	 * 
	 * 
	 * To call other WS, change the parameters below, which are: - the SOAP
	 * Endpoint URL (that is, where the service is responding from) - the SOAP
	 * Action
	 * 
	 * Also change the contents of the method createSoapEnvelope() in this
	 * class. It constructs the inner part of the SOAP envelope that is actually
	 * sent.
	 */
	// "http://dxc-b2b-its-226484134.ap-southeast-2.elb.amazonaws.com/link/InboxEndpoint"
	// String soapEndpointUrl =
	// "http://currencyconverter.kowabunga.net/converter.asmx";
	/*
	 * String soapEndpointUrl =
	 * "http://dxc-b2b-its-226484134.ap-southeast-2.elb.amazonaws.com/link/InboxEndpoint";
	 * String soapAction =
	 * "http://www.ACORD.org/Standards/AcordMsgSvc/Inbox#PostRq";
	 * 
	 * 
	 * callSoapWebService(soapEndpointUrl, soapAction,
	 * "request/SoapXmlRequest1.xml",
	 * "request/Cancellation_QuoteRequest_TC001.xml");
	 */


	private static void callSoapWebService(String soapEndpointUrl, String soapAction, String fileRequest,
			String fileAttached) {
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
			System.out.println();

			// 6. Close connection
			soapConnection.close();
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
	}

	private static SOAPMessage createSOAPRequest(String soapAction, String fileRequest, String... multiFiles)
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
	private static Document xmlStrToDoc(String xmlFile) {
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

	private static SOAPMessage readSoapMessage(String filename) throws SOAPException, FileNotFoundException {
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		soapPart.setContent(new StreamSource(new FileInputStream(filename)));
		message.saveChanges();
		return message;
	}

	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		// SOAP Part
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "wsse";
		// "myNamespace";
		String myNamespaceURI = "http://schemas.xmlsoap.org/soap/envelope/";
		// "http://www.webserviceX.NET";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		/*
		 * Constructed SOAP Request Message: <SOAP-ENV:Envelope
		 * xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
		 * xmlns:myNamespace="http://www.webserviceX.NET"> <SOAP-ENV:Header/>
		 * <SOAP-ENV:Body> <myNamespace:GetInfoByCity> <myNamespace:USCity>New
		 * York</myNamespace:USCity> </myNamespace:GetInfoByCity>
		 * </SOAP-ENV:Body> </SOAP-ENV:Envelope>
		 */
		// SOAP Header
		SOAPHeader soapHeader = envelope.getHeader();
		// soapHeader.addNamespaceDeclaration("wsse",
		// "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		// SOAPElement soapHeaderElem = soapHeader.addChildElement("HeaderInfo",
		// myNamespace);
		// SOAPElement soapHeaderElem1 =
		// soapHeaderElem.addChildElement("HeaderInfo1", myNamespace);
		// soapHeaderElem1.addTextNode("Header content");
		// Header may or may not exist yet

		// if (soapHeader == null)
		// soapHeader = envelope.addHeader();
		// Add WSS Usertoken Element Tree
		SOAPElement security = soapHeader.addChildElement("Security", "wsse",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		SOAPElement userToken = security.addChildElement("UsernameToken", "wsse");
		String username = "svuaignz";
		userToken.addChildElement("Username", "wsse").addTextNode(username);
		String password = "+7SkOsKCuPhBBJx2oRyLGW9ef2w=";
		userToken.addChildElement("Password", "wsse")
				.addAttribute(new QName("Type"),
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText")
				.addTextNode(password);

		SOAPElement nonce = userToken.addChildElement("Nonce", myNamespace);
		nonce.addTextNode("VZgEwSNi/flBXTScjpI7Qg==");

		SOAPElement created = userToken.addChildElement("Created", "wsu",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
		created.addTextNode("2018-02-02T03:37:42.123Z");

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		// SOAPElement soapBodyElem = soapBody.addChildElement("GetInfoByCity",
		// myNamespace);
		// SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("USCity",
		// myNamespace);
		// soapBodyElem1.addTextNode("New York");

		// Create Document from XML file
		try {
			Document contentBody = xmlStrToDoc("D:/07.projects/07.04other/TestSOAP Request/contentBody.xml");
			soapBody.addDocument(contentBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

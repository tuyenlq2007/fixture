package com.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.SAXException;

public class SoapRequest {
	public String post(String url, String headerParams, String fromXmlFile, String toXmlFile) throws Exception {
		HttpPost post = (HttpPost) addHeaderParamsToHttpRequest(new HttpPost(url), headerParams);
		post.setEntity(new StringEntity(new XmlHandle().getFile(fromXmlFile)));
		String responseXml = EntityUtils.toString(new DefaultHttpClient().execute(post).getEntity());
		new XmlHandle().toFile(responseXml, toXmlFile);
		return responseXml;
	}

	public String diffTwoXMLFiles(String file1, String file2)
			throws SAXException, IOException, TransformerConfigurationException, ParserConfigurationException {
		return new XmlHandle().diffTwoXMLFiles(file1, file2);
	}

	public boolean compreTwoXMLFiles(String file1, String file2)
			throws SAXException, IOException, TransformerConfigurationException, ParserConfigurationException {
		return new XmlHandle().compreTwoXMLFiles(file1, file2);
	}
	
	public String extractXML(String xmlFile, String tagName) throws XPathExpressionException, SAXException, IOException,
			ParserConfigurationException, TransformerException {
		return new XmlHandle().extractXML(xmlFile, tagName);
	}

	public String extractXML(String xmlFile, String tagName, String index) throws XPathExpressionException, SAXException, IOException,
	ParserConfigurationException, TransformerException {
	return new XmlHandle().extractXML(xmlFile, tagName, index);
}
	
	protected HttpRequestBase addHeaderParamsToHttpRequest(HttpRequestBase httpRequest, String headerParams) {
		List<String> theHeaderParams = new ArrayList<>(Arrays.asList(headerParams.split(",")));
		int index = 0;
		while (index < theHeaderParams.size() - 1) {
			httpRequest.setHeader(theHeaderParams.get(index).trim(), theHeaderParams.get(index + 1).trim());
			index = index + 2;
		}
		return httpRequest;
	}

}

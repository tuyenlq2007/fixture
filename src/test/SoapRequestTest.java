package test;

import javax.xml.xpath.XPathConstants;

import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.api.SoapRequest;
import com.api.XmlHandle;

public class SoapRequestTest {
	@Test
	public void tryToPostSoapRequest() throws Exception {
		SoapRequest request = new SoapRequest();
		request.post("http://cscvieae528143:14040/axis2/services/GUICRetriveBrokerDetail.GUICRetriveBrokerDetailHttpSoap11Endpoint/","Content-type, text/xml; charset=UTF-8, SOAPAction,", "RQ.xml","temp1.xml");
		org.junit.Assert.assertEquals("PASSED",new XmlHandle().diffTwoXMLFiles("temp1.xml", "temp2.xml"));			
	}
	@Test
	public void tryToExtractXmlElement() throws Exception {
		SoapRequest request = new SoapRequest();
		org.junit.Assert.assertEquals("23053", request.extractXML("temp1.xml", "ax21346:agentno"));	
	}
	@Test
	public void tryToExtractXmlElementbyIndex() throws Exception {
		SoapRequest request = new SoapRequest();
		org.junit.Assert.assertEquals("23053", request.extractXML("temp1.xml", "ax21346:agentno", "0"));	
	}
}
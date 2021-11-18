package test;

import org.junit.Test;

import com.api.FixtureRequests;
import com.jayway.jsonpath.JsonPath;

public class FixtureRequestsTest {
/*	@Test
	public void tryTogetJsonFile() throws Exception {
		FixtureRequests request = new FixtureRequests();
		org.junit.Assert.assertEquals(request.getJsonFile( "temp1.json"),
				request.getJsonFile( "temp.json"));
	}*/
	@Test
	public void tryTocompareJsonFiles() throws Exception {
		FixtureRequests request = new FixtureRequests();
		org.junit.Assert.assertTrue(request.compareJsonFileWithFile("temp.json", "temp1.json"));
	}
/*	
	@Test	
	public void tryToUpdateJsonFileAndGetNewElement() throws Exception {
		String json;
		FixtureRequests request = new FixtureRequests();
		Object res = JsonPath.parse(request.getJsonFile("temp1.json")).put("$.envelop.header", "quotationNo", "QC000001-64").read("$.envelop.header.quotationNo");
		org.junit.Assert.assertEquals("QC000001-64", res.toString());
	}
	
	@Test
	public void tryToUpdateJsonFile() throws Exception {
		FixtureRequests request = new FixtureRequests();
		org.junit.Assert.assertEquals(request.getJsonFile( "temp.json"), request.updateJsonFromFileToDoc("temp1.json", "$.envelop.header", "quotationNo", "QC000001-65"));
		
	}*/
	

	@Test
	public void tryToUpdateJsonFromFileToFile() throws Exception {
		FixtureRequests request = new FixtureRequests();
		request.updateJsonFromFileToFile("temp.json", "$.envelop.header", "quotationNo", "QC000001-66", "temp1.json");
		org.junit.Assert.assertTrue(request.compareJsonFileWithFile("temp.json", "temp1.json"));
		
	}
	
	@Test
	public void tryToExtractJsonElementFromFile() throws Exception {
		FixtureRequests request = new FixtureRequests();
		org.junit.Assert.assertEquals("abc", request.ExtractJsonElementFromFile("temp6.json", "$.[12345]"));
		
	}
	
/*	@Test
	public void tryToExtractIndexJsonFromFile() throws Exception {
		FixtureRequests request = new FixtureRequests();
		org.junit.Assert.assertEquals("114567", request.ExtractIndexJsonFromFile("temp6.json", "OUTPUT_RiskFactorAggregateAfterRiskFactorEmployeeFemaleSingle(0;4)"));
		
	}*/
	
	@Test
	public void tryGet() throws Exception {
		FixtureRequests request = new FixtureRequests();
		org.junit.Assert.assertEquals("QC000001-65", request.Get("http://20.203.7.21/uic_group_ihub/api/MultiDropdownService", "", "", "", "", "Content-Type", "application/json"));
		//org.junit.Assert.assertEquals("QC000001-65", request.Post("http://omni-api-alsvnm-1624151644.ap-southeast-2.elb.amazonaws.com:80/omni-new-business-services/omni/service/quotes", "userName", "Agent@csc.com", "profileId", "12345", "Content-Type", "application/json", "{\"quote-product-id\": \"Professional Indemnity\"}"));
		
	}
	@Test
	public void tryPostWithDataFile() throws Exception {
		FixtureRequests request = new FixtureRequests();
		//org.junit.Assert.assertEquals("", request.sendPost("http://www.technicalkeeda.com/post-request"));
		//org.junit.Assert.assertEquals("QC000001-65", request.PostWithDataFile("http://www.technicalkeeda.com/post-request", "", "", "", "", "Content-Type", "application/json", "temp4.json"));
		//org.junit.Assert.assertEquals("QC000001-65", request.PostWithDataFile("https://uic.group.dev.integral.digital/ihub/api/ValidationSubmissionService", "", "", "", "", "Content-Type", "application/json", "temp.json"));
		//org.junit.Assert.assertEquals("QC000001-65", request.Post("http://omni-api-alsvnm-1624151644.ap-southeast-2.elb.amazonaws.com:80/omni-new-business-services/omni/service/quotes", "userName", "Agent@csc.com", "profileId", "12345", "Content-Type", "application/json", "{\"quote-product-id\": \"Professional Indemnity\"}"));
	}
	
/*	@Test
	public void tryPostBasicAuthentication() throws Exception {
		FixtureRequests request = new FixtureRequests();
		org.junit.Assert.assertEquals("", request.PostBasicAuthentication("https://uic.ipos.dev.integral.digital/ife-infra-oauth2/oauth/token?grant_type=password&client_id=uic-web&username=lle31&password=P@ssword123","uic-web","uicsecret"));
		//org.junit.Assert.assertEquals("QC000001-65", request.PostWithDataFile("http://www.technicalkeeda.com/post-request", "", "", "", "", "Content-Type", "application/json", "temp4.json"));
		//org.junit.Assert.assertEquals("QC000001-65", request.PostWithDataFile("https://uic.group.dev.integral.digital/ihub/api/ValidationSubmissionService", "", "", "", "", "Content-Type", "application/json", "temp.json"));
		//org.junit.Assert.assertEquals("QC000001-65", request.Post("http://omni-api-alsvnm-1624151644.ap-southeast-2.elb.amazonaws.com:80/omni-new-business-services/omni/service/quotes", "userName", "Agent@csc.com", "profileId", "12345", "Content-Type", "application/json", "{\"quote-product-id\": \"Professional Indemnity\"}"));
	}*/
}
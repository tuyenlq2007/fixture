package test;

import org.junit.Test;

import com.api.Rest;

public class RestTest {
	@Test
	public void tryIGetCookie() throws Exception {
		Rest restRequest = new Rest();
		org.junit.Assert.assertEquals("", restRequest.IGetCookie("https://uic.ipos.dev.integral.digital/ife-infra-oauth2/oauth/token?grant_type=password&client_id=uic-web&username=lle31&password=P@ssword123", "uic-web", "uicsecret"));
		//org.junit.Assert.assertEquals("QC000001-65", request.PostWithDataFile("http://www.technicalkeeda.com/post-request", "", "", "", "", "Content-Type", "application/json", "temp4.json"));
		//org.junit.Assert.assertEquals("QC000001-65", request.PostWithDataFile("https://uic.group.dev.integral.digital/ihub/api/ValidationSubmissionService", "", "", "", "", "Content-Type", "application/json", "temp.json"));
		//org.junit.Assert.assertEquals("QC000001-65", request.Post("http://omni-api-alsvnm-1624151644.ap-southeast-2.elb.amazonaws.com:80/omni-new-business-services/omni/service/quotes", "userName", "Agent@csc.com", "profileId", "12345", "Content-Type", "application/json", "{\"quote-product-id\": \"Professional Indemnity\"}"));
	}
}
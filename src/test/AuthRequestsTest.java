package test;

import org.junit.Test;

import com.api.AuthRequests;
import com.api.FixtureRequests;
import com.api.JsonHandle;

public class AuthRequestsTest {
		
	@Test
	public void tryPostBasicAuthentication() throws Exception {
		AuthRequests request = new AuthRequests();
		org.junit.Assert.assertEquals("", (new FixtureRequests()).getDiff((new JsonHandle()).getFile("out.json"), request.postBasicAuthentication("https://uic.ipos.dev.integral.digital/ife-infra-oauth2/oauth/token","grant_type, password, client_id, uic-web, username, lle31, password, P@ssword123", "uic-web","uicsecret")));
	}
}
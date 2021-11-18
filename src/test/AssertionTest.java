package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.api.Assertion;

public class AssertionTest {
	@Test
	public void tryToVerifyAssertion() throws Exception {
		List<List<List<String>>> expectedAssertion = new ArrayList<List<List<String>>>();;
		expectedAssertion.add(Arrays.asList(Arrays.asList("TestCaseID", "1"), Arrays.asList("Request ID", ""),
				Arrays.asList("Request Content", ""), Arrays.asList("Attachment Content", ""),
				Arrays.asList("Expected Response", ""), Arrays.asList("Actual Response", ""),
				Arrays.asList("Status", "")));
		Assertion assertion = new Assertion("1");
		org.junit.Assert.assertEquals(expectedAssertion.toString(), assertion.query());
	}
}
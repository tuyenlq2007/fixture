package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;

public class GetEvidence extends PageApi {
	@Override
	public String execute() throws StopTest {
		return takeScreenShot();
		
	}
}

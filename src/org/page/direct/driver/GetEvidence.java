package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;

public class GetEvidence extends WebApi {
	@Override
	public String execute() throws StopTest {
		return takeScreenShot();
		
	}
}

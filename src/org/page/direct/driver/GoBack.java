package org.page.direct.driver;

import org.api.WebApi;

public class GoBack extends WebApi {

	public GoBack() {
		super();
	}

	@Override
	public String execute() {
		driver.navigate().back();
		return "SUCCESS";
	}

}
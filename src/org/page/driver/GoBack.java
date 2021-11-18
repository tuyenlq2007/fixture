package org.page.driver;

import org.api.PageApi;

public class GoBack extends PageApi {

	public GoBack() {
		super();
	}

	@Override
	public String execute() {
		driver.navigate().back();
		return "SUCCESS";
	}

}
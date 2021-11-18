package org.page.driver;

import org.api.PageApi;

public class Close extends PageApi {

	public Close() {
		super();
	}

	@Override
	public String execute() {
		driver.close();
		return "SUCCESS";
	}

}
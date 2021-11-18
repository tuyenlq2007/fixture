package org.page.direct.driver;

import org.api.WebApi;

public class Close extends WebApi {

	public Close() {
		super();
	}

	@Override
	public String execute() {
		driver.close();
		return "SUCCESS";
	}

}
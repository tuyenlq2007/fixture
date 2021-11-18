package org.page.driver;

import org.api.PageApi;
import org.configuration.SetUp;
import org.selenium.session.DriverPool;

public class SwitchToFrame extends PageApi {
	private final String framename;

	public SwitchToFrame(String framename) {
		super();
		this.framename = framename;
	}

	@Override
	public String execute() {
		DriverPool.INSTANCE.switch_frame(SetUp.getSetup().getDriver(), framename);
		return "SUCCESS";
	}

}
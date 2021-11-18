package org.page.direct.driver;

import org.api.WebApi;
import org.configuration.SetUp;
import org.selenium.session.DriverPool;

public class SwitchToFrame extends WebApi {
	private final String frameid;

	public SwitchToFrame(String frameid) {
		super();
		this.frameid = frameid;
	}

	@Override
	public String execute() {
		DriverPool.INSTANCE.switch_parentframe(SetUp.getSetup().getDriver());
		//DriverPool.INSTANCE.switch_frame(SetUp.getSetup().getDriver(), framename);
		driver.switchTo().frame(Integer.parseInt(frameid));
		return "SUCCESS";
	}

}
package org.page.direct.driver;

import org.api.WebApi;
import org.configuration.SetUp;
import org.selenium.session.DriverPool;

public class SwitchtoParentFrame extends WebApi {

	public SwitchtoParentFrame() {
		super();
	}

	@Override
	public String execute() {
		DriverPool.INSTANCE.switch_parentframe(SetUp.getSetup().getDriver());
		return "SUCCESS";
	}

}
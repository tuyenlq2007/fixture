package org.page.driver;

import org.api.PageApi;
import org.configuration.SetUp;
import org.selenium.session.DriverPool;

public class SwitchtoParentFrame extends PageApi {

	public SwitchtoParentFrame() {
		super();
	}

	@Override
	public String execute() {
		DriverPool.INSTANCE.switch_parentframe(SetUp.getSetup().getDriver());
		return "SUCCESS";
	}

}
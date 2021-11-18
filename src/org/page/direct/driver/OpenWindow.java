package org.page.direct.driver;

import java.util.Set;

import org.api.WebApi;
import org.openqa.selenium.JavascriptExecutor;

public class OpenWindow extends WebApi {
	private final String url;

	public OpenWindow(String url) {
		super();
		this.url = url;
	}

	@Override
	public String execute() {
		final String result = "SUCCESS";
		final Set<String> windows = driver.getWindowHandles();
		final String adminToolHandle = driver.getWindowHandle();
		((JavascriptExecutor) driver).executeScript("window.open();");
		final Set<String> customerWindow = driver.getWindowHandles();
		customerWindow.removeAll(windows);
		final String customerSiteHandle = (String) customerWindow.toArray()[0];
		driver.switchTo().window(adminToolHandle);
		driver.close();
		driver.switchTo().window(customerSiteHandle);
		driver.get(url);
		return result;
	}
}
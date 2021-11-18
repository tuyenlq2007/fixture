package org.page.direct.verification;

import java.util.ArrayList;
import java.util.List;

import org.api.WebVerificationApi;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GetTextOfChildren extends WebVerificationApi {
	public GetTextOfChildren(String object_repository) {
		super(object_repository);
	}

	@Override
	public List<String> executes() throws Exception {
		final List<String> results = new ArrayList<String>();
		try {
			final WebElement rootWebElement = validateElement();
			final List<WebElement> childs = rootWebElement.findElements(By.xpath(".//*"));
			for (final WebElement child : childs) {
				results.add(child.getText());
			}
		} catch (final Exception e) { // or your specific exception
			// log
			// screen shot
			throw new Exception(takeScreenShot(), e);
		}
		// results.add("asdfds");
		return results;

	}

}
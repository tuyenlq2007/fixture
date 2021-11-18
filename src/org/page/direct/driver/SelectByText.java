package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectByText extends WebApi {
	private final String text;

	public SelectByText(String object_repository, String text) {
		super(object_repository);
		this.text = text;
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		final WebElement element = validateElement();
		final Select select = new Select(element);
		if (select.isMultiple()) {
			select.deselectAll();
		}
		select.selectByVisibleText(text);
		return result;
	}

}
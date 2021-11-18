package org.page.direct.driver;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectByValue extends WebApi {
	private final String selectValue;

	public SelectByValue(String object_repository, String selectValue) {
		super(object_repository);
		this.selectValue = selectValue;
	}

	@Override
	public String execute() throws StopTest {
		final String result = "SUCCESS";
		final WebElement element = validateElement();
		final Select select = new Select(element);
		if (select.isMultiple()) {
			select.deselectAll();
		}
		select.selectByValue(selectValue);
		return result;
	}

}
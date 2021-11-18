package org.page.verification;

import java.util.ArrayList;
import java.util.List;

import org.api.PageVerificationApi;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GetSelected extends PageVerificationApi {
	String type;
	String isMultiple;

	public GetSelected(String object_repository, String type, String isMultiple) {
		super(object_repository);
		this.type = type;
		this.isMultiple = isMultiple;
	}

	@Override
	public String execute() throws Exception {
		final WebElement select = validateElement();
		if (select == null) {
			return "null";
		}
		final List<WebElement> options = select.findElements(By.tagName("option"));
		final List<String> found = new ArrayList<>();
		int i = -1;
		for (final WebElement option : options) {
			i++;
			if (option.isSelected()) {
				switch (type.toUpperCase()) {
				case "LABEL":
					found.add(option.getText());
					break;
				case "VALUE":
					found.add(option.getAttribute("value"));
					break;
				case "INDEX":
					found.add(Integer.toString(i));
					break;
				case "ID":
					found.add(option.getAttribute("id"));
					break;
				default:
					throw new UnsupportedOperationException(type + " is not implemented.");
				}
			}
		}

		if (isMultiple.toUpperCase() == "YES") {
			return found.toString();
		} else {
			return found.isEmpty() ? null : found.get(0);
		}

	}
}
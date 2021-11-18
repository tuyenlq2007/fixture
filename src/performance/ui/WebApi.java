package performance.ui;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.WebElement;

public class WebApi extends PageApi {
	public WebApi(String object_repository) {
		super(object_repository);
	}

	public WebElement validateElement() throws StopTest {

	return super.findElement(super.m_pro.getProperty(object_repository));

	}

}

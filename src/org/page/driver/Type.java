package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Type extends PageApi {
	private final String enterValue;

	public Type(String enterValue, String object_repository) {
		super(object_repository);
		this.enterValue = enterValue;
	}

	/*
	 * public ExpectedCondition<Boolean> angularHasFinishedProcessing() { return
	 * new ExpectedCondition<Boolean>() {
	 * 
	 * @Override public Boolean apply(WebDriver driver) { String
	 * hasAngularFinishedScript =
	 * "try {var callback = arguments[arguments.length - 1];\n" +
	 * "var el = document.querySelector('html');\n" + "if (!window.angular) {\n"
	 * + "    callback('false')\n" + "}\n" + "if (angular.getTestability) {\n" +
	 * "    angular.getTestability(el).whenStable(function(){callback('true')});\n"
	 * + "} else {\n" + "    if (!angular.element(el).injector()) {\n" +
	 * "        callback('false')\n" + "    }\n" +
	 * "    var browser = angular.element(el).injector().get('$browser');\n" +
	 * "    browser.notifyWhenNoOutstandingRequests(function(){callback('true')});\n"
	 * + "} }catch (e){'true'};";
	 * 
	 * JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
	 * String isProcessingFinished =
	 * javascriptExecutor.executeAsyncScript(hasAngularFinishedScript)
	 * .toString();
	 * 
	 * return Boolean.valueOf(isProcessingFinished); } }; }
	 */

	@Override
	public String execute() throws StopTest {
		String result = "SUCCESS";
		try {
			/*
			 * driver.manage().timeouts().setScriptTimeout(15,
			 * TimeUnit.SECONDS); WebDriverWait wait = new WebDriverWait(driver,
			 * 3, 20); wait.until(angularHasFinishedProcessing());
			 */
			final WebElement element = validateElement();
			final String tagName = element.getTagName().toLowerCase();
			switch (tagName) {
			case "input":
				final String type = element.getAttribute("type");
				if (type != null && "file".equalsIgnoreCase(type)) {
					result = "You should be using attachFile to set the value of a file input element";
				}
				element.clear();
				element.sendKeys(enterValue);
				break;
			default:
				((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
				element.sendKeys(enterValue);
				break;
			}
		} catch (final Exception e) {
			try {
				final WebElement element = validateElement();
				final String tagName = element.getTagName().toLowerCase();
				switch (tagName) {
				case "input":
					final String type = element.getAttribute("type");
					if (type != null && "file".equalsIgnoreCase(type)) {
						result = "You should be using attachFile to set the value of a file input element";
					}
					element.clear();
					element.sendKeys(enterValue);
					break;
				default:
					((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
					element.sendKeys(enterValue);
					break;
				}
			} catch (final Exception e1) {
				throw new StopTest(takeScreenShot(), e1);
			}

		}
		return result;
	}

}
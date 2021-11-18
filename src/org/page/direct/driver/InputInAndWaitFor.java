package org.page.direct.driver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.api.WebApi;
import org.exception.StopTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class InputInAndWaitFor extends WebApi {
	private final String enterValue;
	private final String waitLocator;

	public InputInAndWaitFor(String enterValue, String locator, String waitLocator) {
		super(locator);
		this.enterValue = enterValue;
		this.waitLocator = waitLocator;
	}

	@Override
	public String execute() throws StopTest {
		String result = "SUCCESS";
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		final StringSelection str = new StringSelection(enterValue);
		clipboard.setContents(str, null);

		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		String element = returnXpath();
		// element = "//*[@id='insuredobject-car-registration-number']";
		String scriptInput = "function getElementByXpath(path) { return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}; getElementByXpath(\"actualxpath\").value = \"actualvalue\" ;";
		scriptInput = scriptInput.replaceAll("actualxpath", element);
		scriptInput = scriptInput.replaceAll("actualvalue", enterValue);
		String scriptResult = "function getElementByXpath(path) { return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}; return getElementByXpath(\"actualxpath\").value ;";
		scriptResult = scriptResult.replaceAll("actualxpath", element);
		WebElement ele = validateElement();
		try {
			ele.click();
			putString(enterValue);
			String scriptResult1 = (String) executor.executeScript(scriptResult);
			if (!scriptResult1.toUpperCase().equals(enterValue.toUpperCase())) {
				Thread.sleep(3000);
				executor.executeScript(scriptInput);
			}
			
			waiforsync(waitLocator);
			scriptResult1 = (String) executor.executeScript(scriptResult);
			if (!scriptResult1.toUpperCase().equals(enterValue.toUpperCase())) {
				Thread.sleep(3000);
				ele.clear();
				putString(enterValue);
			}
		} catch (final Exception e) {
			try {
				Thread.sleep(3000);
				ele.click();
				putString(enterValue);
				String scriptResult1 = (String) executor.executeScript(scriptResult);
				if (!scriptResult1.toUpperCase().equals(enterValue.toUpperCase())) {
					Thread.sleep(3000);
					executor.executeScript(scriptInput);
				}
				waiforsync(waitLocator);
				scriptResult1 = (String) executor.executeScript(scriptResult);
				if (!scriptResult1.toUpperCase().equals(enterValue.toUpperCase())) {
					Thread.sleep(3000);
					ele.clear();
					putString(enterValue);
				}
			} catch (final Exception e1) {
				try {
					Thread.sleep(3000);
					putString(enterValue);
					String scriptResult1 = (String) executor.executeScript(scriptResult);
					if (!scriptResult1.toUpperCase().equals(enterValue.toUpperCase())) {
						Thread.sleep(3000);
						executor.executeScript(scriptInput);
					}
					waiforsync(waitLocator);
					scriptResult1 = (String) executor.executeScript(scriptResult);
					if (!scriptResult1.toUpperCase().equals(enterValue.toUpperCase())) {
						Thread.sleep(3000);
						ele.clear();
						putString(enterValue);
					}
				} catch (final Exception e2) {
					throw new StopTest(takeScreenShot(), e2);
				}

			}

		}
		return result;
	}

	private void waiforsync(String xpath) throws InterruptedException {
		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		String script = "function getElementByXpath(path) {  return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;}; if (getElementByXpath(\"actualxpath\") == null){ return false } else {return true} ;";
		script = script.replaceAll("actualxpath", xpath);
		boolean result = (boolean) executor.executeScript(script);
		Long timeout = (long) 18000;
		Thread.sleep(1000);
		while (result && timeout > 0) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result = (boolean) executor.executeScript(script);
			timeout = timeout - 3000;
		}
	}

	public void putString(String policyNumber) throws AWTException {
		for (int iterator = 0; iterator < policyNumber.length(); iterator++) {
			char eachPolicyNumber = policyNumber.charAt(iterator);
			sendKey(eachPolicyNumber);
		}
	}

	private void sendKey(char inputKey) throws AWTException {
		Robot robot = new Robot();
		switch (inputKey) {
		case '0':
			robot.keyPress(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_0);
			break;
		case '1':
			robot.keyPress(KeyEvent.VK_1);
			robot.keyRelease(KeyEvent.VK_1);
			break;
		case '2':
			robot.keyPress(KeyEvent.VK_2);
			robot.keyRelease(KeyEvent.VK_2);
			break;
		case '3':
			robot.keyPress(KeyEvent.VK_3);
			robot.keyRelease(KeyEvent.VK_3);
			break;
		case '4':
			robot.keyPress(KeyEvent.VK_4);
			robot.keyRelease(KeyEvent.VK_4);
			break;
		case '5':
			robot.keyPress(KeyEvent.VK_5);
			robot.keyRelease(KeyEvent.VK_5);
			break;
		case '6':
			robot.keyPress(KeyEvent.VK_6);
			robot.keyRelease(KeyEvent.VK_6);
			break;
		case '7':
			robot.keyPress(KeyEvent.VK_7);
			robot.keyRelease(KeyEvent.VK_7);
			break;
		case '8':
			robot.keyPress(KeyEvent.VK_8);
			robot.keyRelease(KeyEvent.VK_8);
			break;
		case '9':
			robot.keyPress(KeyEvent.VK_9);
			robot.keyRelease(KeyEvent.VK_9);
			break;
		case 'A':
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			break;
		case 'a':
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			break;
		case 'B':
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			robot.keyPress(KeyEvent.VK_B);
			robot.keyRelease(KeyEvent.VK_B);
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			break;
		case 'b':
			robot.keyPress(KeyEvent.VK_B);
			robot.keyRelease(KeyEvent.VK_B);
			break;
		case 'C':
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			break;
		case 'c':
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
			break;
		case 'D':
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			robot.keyPress(KeyEvent.VK_D);
			robot.keyRelease(KeyEvent.VK_D);
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			break;
		case 'd':
			robot.keyPress(KeyEvent.VK_D);
			robot.keyRelease(KeyEvent.VK_D);
			break;
		case 'E':
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			robot.keyPress(KeyEvent.VK_E);
			robot.keyRelease(KeyEvent.VK_E);
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			break;
		case 'e':
			robot.keyPress(KeyEvent.VK_E);
			robot.keyRelease(KeyEvent.VK_E);
			break;
		case 'F':
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			robot.keyPress(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_F);
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			break;
		case 'f':
			robot.keyPress(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_F);
			break;
		case 'G':
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			robot.keyPress(KeyEvent.VK_G);
			robot.keyRelease(KeyEvent.VK_G);
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			break;
		case 'g':
			robot.keyPress(KeyEvent.VK_G);
			robot.keyRelease(KeyEvent.VK_G);
			break;
		case 'H':
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			robot.keyPress(KeyEvent.VK_H);
			robot.keyRelease(KeyEvent.VK_H);
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			break;
		case 'h':
			robot.keyPress(KeyEvent.VK_H);
			robot.keyRelease(KeyEvent.VK_H);
			break;
		case 'J':
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			robot.keyPress(KeyEvent.VK_J);
			robot.keyRelease(KeyEvent.VK_J);
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			break;
		case 'j':
			robot.keyPress(KeyEvent.VK_J);
			robot.keyRelease(KeyEvent.VK_J);
			break;
		case 'K':
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			robot.keyPress(KeyEvent.VK_K);
			robot.keyRelease(KeyEvent.VK_K);
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			break;
		case 'k':
			robot.keyPress(KeyEvent.VK_K);
			robot.keyRelease(KeyEvent.VK_K);
			break;
		case 'L':
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			robot.keyPress(KeyEvent.VK_L);
			robot.keyRelease(KeyEvent.VK_L);
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			break;
		case 'l':
			robot.keyPress(KeyEvent.VK_L);
			robot.keyRelease(KeyEvent.VK_L);
			break;
		case 'M':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_M);
			robot.keyRelease(KeyEvent.VK_M);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'm':
			robot.keyPress(KeyEvent.VK_M);
			robot.keyRelease(KeyEvent.VK_M);
			break;
		case 'N':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_N);
			robot.keyRelease(KeyEvent.VK_N);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'n':
			robot.keyPress(KeyEvent.VK_N);
			robot.keyRelease(KeyEvent.VK_N);
			break;
		case 'O':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_O);
			robot.keyRelease(KeyEvent.VK_O);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'o':
			robot.keyPress(KeyEvent.VK_O);
			robot.keyRelease(KeyEvent.VK_O);
			break;
		case 'P':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_P);
			robot.keyRelease(KeyEvent.VK_P);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'p':
			robot.keyPress(KeyEvent.VK_P);
			robot.keyRelease(KeyEvent.VK_P);
			break;
		case 'Q':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_Q);
			robot.keyRelease(KeyEvent.VK_Q);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'q':
			robot.keyPress(KeyEvent.VK_Q);
			robot.keyRelease(KeyEvent.VK_Q);
			break;
		case 'R':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_R);
			robot.keyRelease(KeyEvent.VK_R);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'r':
			robot.keyPress(KeyEvent.VK_R);
			robot.keyRelease(KeyEvent.VK_R);
			break;
		case 'S':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 's':
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_S);
			break;
		case 'T':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 't':
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_T);
			break;
		case 'U':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_U);
			robot.keyRelease(KeyEvent.VK_U);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'u':
			robot.keyPress(KeyEvent.VK_U);
			robot.keyRelease(KeyEvent.VK_U);
			break;
		case 'V':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'v':
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			break;
		case 'W':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'w':
			robot.keyPress(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_W);
			break;
		case 'X':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_X);
			robot.keyRelease(KeyEvent.VK_X);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'x':
			robot.keyPress(KeyEvent.VK_X);
			robot.keyRelease(KeyEvent.VK_X);
			break;
		case 'Y':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_Y);
			robot.keyRelease(KeyEvent.VK_Y);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'y':
			robot.keyPress(KeyEvent.VK_Y);
			robot.keyRelease(KeyEvent.VK_Y);
			break;
		case 'Z':
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_Z);
			robot.keyRelease(KeyEvent.VK_Z);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			break;
		case 'z':
			robot.keyPress(KeyEvent.VK_Z);
			robot.keyRelease(KeyEvent.VK_Z);
			break;

		default:
			break;
		}
	}
}

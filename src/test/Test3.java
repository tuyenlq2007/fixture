package test;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.configuration.SetUp;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Test3 {
	public static void main(String[] args) {
		DesiredCapabilities caie = DesiredCapabilities.internetExplorer();
		caie.setCapability("InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION", true);
		caie.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		caie.setCapability("ignoreZoomSetting", true);
		caie.setCapability("ignoreProtectedModeSettings", true);
		caie.setCapability("initialBrowserUrl", "http://10.200.110.53:8101/LifeAsiaWeb/FirstServlet?HTTP_OAM_USERID=CSCUSER1");
		caie.setCapability("initialBrowserUrl", SetUp.getSetup().getInitialBrowserUrl());
		caie.setCapability("requireWindowFocus", "true");
		caie.setCapability("nativeEvents", true);
		caie.setCapability("browserFocus", true);
		System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
		// newDriver = new InternetExplorerDriver(caie);
		// InternetExplorerDriver driver = new
		// InternetExplorerDriver(caie);
		/*
		 * DesiredCapabilities caie =
		 * DesiredCapabilities.internetExplorer(); caie.setCapability(
		 * "InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION", true);
		 * caie.setCapability(InternetExplorerDriver.
		 * INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		 * caie.setCapability("ignoreZoomSetting", true);
		 * caie.setCapability("ignoreProtectedModeSettings", true);
		 * caie.setCapability("initialBrowserUrl",
		 * "http://10.200.110.53:8101/LifeAsiaWeb/FirstServlet?HTTP_OAM_USERID=CSCUSER1"
		 * );
		 * 
		 * System.setProperty("webdriver.ie.driver",
		 * "C:\\ITS\\selenium\\IEDriverServer.exe");
		 */
		

		try {
			WebDriver newDriver = new RemoteWebDriver(new URL("http://localhost:443/wd/hub"), caie);
			newDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			// String currentWindow = newDriver.getWindowHandle();
			newDriver.switchTo().window(newDriver.getWindowHandle());
			newDriver.manage().window().maximize();
			// WebElement element = newDriver.findElement(By.name("q"));

			// element.sendKeys("abc");
			// element.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
			// element.submit();

			List<WebElement> list = newDriver.findElements(By.tagName("input"));
			System.out.println("Number of links: " + list.size());
			String outer = "";
			for (int i = 0; i < list.size(); i++) {
				outer = outer + "index: " + i + " -- html: " + list.get(i).getAttribute("outerHTML")+"\n";
				
			}
			
			System.out.println(outer);
			((JavascriptExecutor) newDriver).executeScript("arguments[0].value = 'abc';", list.get(1));
			
			//newDriver.quit();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}

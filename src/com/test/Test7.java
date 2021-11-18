package com.test;


import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.api.Transporter;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;

public class Test7 {

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
		//Transporter tr = new Transporter();
		//System.out.println(tr.getXmlNodeByIdAndXpath("c3d9bb27-c418-7060-96ba-14f4d489f315", "//PolicyNewBusinessQuotationProcessResult/PolicyQuotation/AssignedIdentifier/Id"));
		/*IOSDriver driver;
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
		// cap.setCapability("platformVersion","11.0");
		cap.setCapability("platformVersion", "11.1.2");
		// cap.setCapability("udid","fd512be00c295285696b982540939bad10597c34");
		// // ipad 10
		// cap.setCapability("udid", "F10B07A5-B0C2-40AA-B680-ACD3147FA4F5"); //
		// ipad 11
		cap.setCapability("udid", "db76172f264137e15fcad92d844f466be8ac6776"); // iPad
																				// Air
																				// 2
		cap.setCapability("deviceName", "iPad Air 2");
		cap.setCapability("platformName", "iOS");
		cap.setCapability("browserName", "Safari");
		cap.setCapability("safariInitialUrl",
				"https://uat-esapphire.aviva.com.sg/afa-ba-latest/ife-web-base-agent/login");
		cap.setCapability("bootstrapPath",
				"/usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent");
		cap.setCapability("agentPath",
				"/usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent/WebDriverAgent.xcodeproj");
		cap.setCapability("xcodeOrgId", "J69WASBDSB");
		cap.setCapability("xcodeSigningId", "iPhone Developer");
		cap.setCapability("autoGrantPermissions", "true");
		cap.setCapability("autoAcceptAlerts", "true");
		cap.setCapability(MobileCapabilityType.NO_RESET, true);
		driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap); // 20.203.185.9

		TouchAction action = new TouchAction(driver);
		action.press(PointOption.point(1280, 1013));
		action.moveTo(PointOption.point(1280, 1013));
		action.release();
		action.perform();*/

	}
}

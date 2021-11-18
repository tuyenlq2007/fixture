package test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Test {

	public static void main(String[] args) throws InterruptedException, AWTException {
		Robot robot = new Robot();
		System.setProperty("webdriver.chrome.driver", "C:\\ITS\\selenium\\chromedriver.exe");
		// WebDriver driver = new ChromeDriver();
		// driver.get("https://magic.dev.r193.dxchub.com/omnichannel-ui-lifesuite/");

		ChromeOptions options = new ChromeOptions();
		options.addExtensions(new File("C:\\ITS\\selenium\\extension_3_16_1_0.crx"));

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		ChromeDriver driver = new ChromeDriver(capabilities);
		
		System.out.println("Opening extension");
		//driver.get("chrome-extension://mooikfkahbdckldjjndioackbalphokd/index.html");
		driver.get("chrome-extension://leonofcckgmedihikplhnngeppmdncce/index.html");

		driver.navigate().refresh();
		System.out.println("Refresh successfully");
		Thread.sleep(3000);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_O);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Thread.sleep(1000);
		String filename = "CR431.039";
		String text = "C:\\SOMPO\\" + filename + ".side";
		//"C:\\SOMPO\\cr43-part1.side";
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_R);
		robot.keyRelease(KeyEvent.VK_R);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		/*
		 * try { new NgWebDriver((JavascriptExecutor)
		 * driver).waitForAngularRequestsToFinish(); } catch (TimeoutException
		 * exception) { exception.printStackTrace(); } WebElement firstname =
		 * driver.findElement(ByAngular.model("username"));
		 * 
		 * driver.findElement(By.xpath("//*[@id=\"undefined\"]/div[2]/div[2]/input")).
		 * sendKeys("Agent007@dxc.com");
		 * driver.findElement(By.xpath("//*[@id=\"undefined\"]/div[2]/div[4]/input")).
		 * sendKeys("Agent007@dxc.com");
		 * driver.findElement(By.xpath("//*[@id=\"undefined\"]/div[2]/div[6]/button")).
		 * click();
		 */
		Thread.sleep(50000);
		String html = driver.getPageSource();
		System.out.println(html);
		
		driver.close();


	}

}

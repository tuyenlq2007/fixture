/**
 *
 */
/**
 * @author tle242
 *
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Test2 {
	public static void main(String[] args) {
		/*
		 * // Declare the JDBC objects. Connection con = null; Statement stmt =
		 * null; ResultSet rs = null;
		 * 
		 * try { con = getSQLServerConnection_SQLJDBC();
		 * 
		 * // Create and execute an SQL statement that returns some data. String
		 * SQL = "SELECT * from chdrpf"; stmt = con.createStatement(); rs =
		 * stmt.executeQuery(SQL);
		 * 
		 * // Iterate through the data in the result set and display it. while
		 * (rs.next()) { System.out.println(rs.getString(2)); } } catch
		 * (Exception e) { System.out.println(e.toString()); } finally { if (rs
		 * != null) try { rs.close(); } catch (Exception e) { } if (stmt !=
		 * null) try { stmt.close(); } catch (Exception e) { } if (con != null)
		 * try { con.close(); } catch (Exception e) { } }
		 */
		/*
		 * System.setProperty("webdriver.ie.driver",
		 * "C:\\ITS\\selenium\\" + "IEDriverServer.exe"); WebDriver driver = new
		 * InternetExplorerDriver();
		 */

		/*
		 * DesiredCapabilities capabilities =
		 * DesiredCapabilities.internetExplorer();
		 * capabilities.setCapability(InternetExplorerDriver.
		 * INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		 * System.setProperty("webdriver.ie.driver",
		 * "C:\\ITS\\selenium\\IEDriverServer.exe"); WebDriver driver = new
		 * InternetExplorerDriver(capabilities); try { Thread.sleep(20000); }
		 * catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * driver.get("http://www.wikipedia.org/");
		 * System.out.println(driver.getTitle());
		 * System.out.println(driver.getCurrentUrl());
		 * System.out.println(driver.getPageSource()); try { Thread.sleep(5000);
		 * } catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * driver.findElement(By.id("searchInput")).sendKeys("Selenium");
		 */
		/*InternetExplorerOptions options = new InternetExplorerOptions();
		options.IntroduceInstabilityByIgnoringProtectedModeSettings = true;
		options.RequireWindowFocus = true;
		driver = new InternetExplorerDriver(options);*/
		
		
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
/*		capabilities.setCapability("InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION", true);
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability("ignoreZoomSetting", true);
		capabilities.setCapability("ignoreProtectedModeSettings", true);
		capabilities.setCapability("initialBrowserUrl", "http://wiki.com/");
		capabilities.setCapability("requireWindowFocus","true");
		capabilities.setCapability("nativeEvents",true);
		capabilities.setCapability("browserFocus",true);
		*/
		System.setProperty("webdriver.ie.driver", "C:\\ITS\\selenium\\IEDriverServer.exe");

		WebDriver driver = new InternetExplorerDriver(capabilities);
		driver.manage().window().maximize();
		driver.get("http://wiki.com/");

		
		//final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//final StringSelection str = new StringSelection("afddsaf");
		//clipboard.setContents(str, null);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement element = driver.findElement(By.name("q"));
		
		element.sendKeys("abc");
		//element.sendKeys(Keys.chord(Keys.CONTROL, "v"), "");
		element.submit();
		driver.quit();
//chrome-extension://hehijbfgiekmjfkfjpbkbammjbdenadd/nhc.htm#url=https://www.google.com/
		
		// .sendKeys("Manoj Test");

		// driver.executeScript("document.getElementsByName('q')[0].setAttribute('value',
		// 'new value for element')");

	}

	// Connect to SQLServer.
	// (Using SQLJDBC)
	public static Connection getSQLServerConnection_SQLJDBC() throws ClassNotFoundException, SQLException {
		String hostName = "13.250.84.21";
		String sqlInstanceName = "SQLEXPRESS";
		String database = "BRI_LIFE_FT_174";
		String userName = "Integral";
		String password = "P@ssword123";

		return getSQLServerConnection_SQLJDBC(hostName, sqlInstanceName, database, userName, password);
	}

	// SQLServer & SQLJDBC.
	private static Connection getSQLServerConnection_SQLJDBC(String hostName, String sqlInstanceName, String database,
			String userName, String password) throws ClassNotFoundException, SQLException {
		// Declare the class Driver for Oracle DB
		// This is necessary with Java 5 (or older)
		// Java6 (or newer) automatically find the appropriate driver.
		// If you use Java> 5, then this line is not needed.
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		// Example:
		// jdbc:sqlserver://ServerIp:1433/SQLEXPRESS;databaseName=simplehr
		String connectionURL = "jdbc:sqlserver://" + hostName + ":1433" + ";instance=" + sqlInstanceName
				+ ";databaseName=" + database;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}
}

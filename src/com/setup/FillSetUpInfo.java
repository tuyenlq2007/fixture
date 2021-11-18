package com.setup;

import java.io.IOException;
import java.util.Scanner;

import org.configuration.SetUp;

public class FillSetUpInfo {
	private final SetUp setUp = new SetUp();

	/*
	 * public boolean fillPassword(final String sValue) { setUp.setpassword(sValue);
	 * return true; }
	 * 
	 * public boolean fillUsername(final String sValue) { setUp.setuserName(sValue);
	 * return true; }
	 * 
	 * public boolean fillDatabase(final String sValue) { setUp.setdatabase(sValue);
	 * return true; }
	 * 
	 * public boolean fillSqlInstanceName(final String sValue) {
	 * setUp.setsqlInstanceName(sValue); return true; }
	 * 
	 * public boolean fillHostname(final String sValue) { setUp.sethostName(sValue);
	 * return true; }
	 */
		
	public boolean fillBrowser(final String sValue) {
		setUp.setBrowser(sValue);
		return true;
	}

	/*
	 * //Android public boolean fillUdid(final String sValue) {
	 * setUp.setUdid(sValue); return true; }
	 * 
	 * public boolean fillPlatformVersion(final String sValue) {
	 * setUp.setPlatformVersion(sValue); return true; }
	 * 
	 * public boolean fillAppPackage(final String sValue) {
	 * setUp.setAppPackage(sValue); return true; }
	 * 
	 * public boolean fillAppActivity(final String sValue) {
	 * setUp.setAppActivity(sValue); return true; } //End Android
	 */	
	public boolean fillDriver(final int iValue) {
		setUp.setDriver(iValue);
		return true;

	}

	public boolean fillPropertiesFile(final String sValue) {
		setUp.setPropertyFile(sValue);
		return true;
	}

	public boolean fillProperty(final String sPropertiesFile, final String sProperty, final String sValue) {
		setUp.setProperty(sPropertiesFile, sProperty, sValue);
		return true;
	}

	public boolean fillRemoteSlaveNode(final String sValue) {
		setUp.setRemoteSlaveNode(sValue);
		return true;
	}

	public boolean fillSpeed(final int iValue) {
		setUp.setSpeed(iValue);
		return true;

	}
	
	public boolean fillInitialBrowserUrl(final String sValue) {
		setUp.setinitialBrowserUrl(sValue);
		return true;

	}
	
	public boolean startDriver(String listeningPort) throws IOException {
		Runtime.getRuntime().exec("cmd /c start java -jar C:\\ITS\\selenium\\selenium-server-standalone-2.53.1.jar -port " + listeningPort);
		return true;

	}

	public boolean stopDriver(String listeningPort) throws IOException, InterruptedException {
		String pid = "";
		final Process p = Runtime.getRuntime().exec("cmd.exe /c netstat -ano ");
		final Scanner sc = new Scanner(p.getInputStream(), "IBM850");
		while (sc.hasNextLine()) {
			final String line = sc.nextLine();
			if (line.toLowerCase().contains(listeningPort)) {
				pid = line.substring(line.lastIndexOf(" ")).trim();
			}
		}
		sc.close();
		Runtime.getRuntime().exec("taskkill /F /PID " + pid);
		Runtime.getRuntime().exec("taskkill /f /im cmd.exe");

		return true;

	}
	
}
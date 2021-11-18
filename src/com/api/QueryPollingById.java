package com.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;

public class QueryPollingById {
	private List<List<List<String>>> getContent;
	private List<String> ignoredNode;

	public QueryPollingById(String id, String timeout, String pollingTime) throws Exception {
		// Thread.sleep(Integer.parseInt(timeout));
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream(System.getenv("ITS") + File.separator + "jdbc.properties");
		// load a properties file
		prop.load(input);
		getContent = new ArrayList<List<List<String>>>();
		ignoredNode = new ArrayList();
		Integer iTimeout = Integer.parseInt(timeout);
		Integer iPollingTime = Integer.parseInt(pollingTime);
		int row = 0;
		String theExpectedresult = "";
		String comparisonOfResults = "";
		getContent = new ArrayList<List<List<String>>>();
		ignoredNode = new ArrayList();
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":"
					+ prop.getProperty("port")
					+ "/demo?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"tle242@csc.com", "root");

		} catch (ClassNotFoundException | SQLException e) {
			connection = null;

			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		ResultSet rstemp = null;
		PreparedStatement pr = null;

		String sqltemp = "SELECT * FROM Polling WHERE id = ? AND actualresult <>  '' AND actualresult IS NOT NULL ";
		while (iTimeout > 0) {
			try {
				pr = connection.prepareStatement(sqltemp);
				pr.setString(1, id);
				rstemp = pr.executeQuery();
				if (!rstemp.next()) {
					Thread.sleep(iPollingTime);
					iTimeout -= iPollingTime;
				} else {
					iTimeout = 0;
				}
				rstemp.close();

			} catch (SQLException e) {
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
			} finally {
				if (pr != null) {
					try {
						pr.close();
					} catch (SQLException e) {
					}
				}
			}
		}

		DataPolling p = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM Polling WHERE id = ?";
			// String sql = "SELECT * FROM Polling";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Retrieve by column name
				// String id = rs.getString("id");
				String request = rs.getString("request");
				String env = rs.getString("enviroment");
				String host = rs.getString("host");
				String testcasename = rs.getString("testcasename");
				String excluding = rs.getString("excluding");
				String body = rs.getString("body");
				String actualresult = rs.getString("actualresult");
				String expectedresult = rs.getString("expectedresult");
				String comparision = rs.getString("comparision");
				String status = rs.getString("status");
				Date starttime = null;
				Date endtime = null;
				if (rs.getDate("starttime") != null) {
					java.sql.Timestamp dbSqlTimestamp = rs.getTimestamp("starttime");
					starttime = new java.util.Date(dbSqlTimestamp.getTime());
				}
				if (rs.getDate("endtime") != null) {
					java.sql.Timestamp dbSqlTimestamp = rs.getTimestamp("endtime");
					endtime = new java.util.Date(dbSqlTimestamp.getTime());
				}

				if (actualresult.isEmpty() || actualresult.trim() == "") {
					row++;
					getContent.add(Arrays.asList(Arrays.asList("Environment/Endpoint", env),
							Arrays.asList("TestCaseID", testcasename), Arrays.asList("Request ID", id),
							Arrays.asList("Request Content", toNewFile(request, host, "RequestContent")),
							Arrays.asList("Attachment Content",
									host + File.separator + "files" + File.separator + body),
							Arrays.asList("Expected Response",
									host + File.separator + "files" + File.separator + expectedresult),
							Arrays.asList("Actual Response", ""), Arrays.asList("Status", "PENDING"),
							Arrays.asList("Response Times (ms)", ""), Arrays.asList("Comparison of Results", "")));
				}

				if (!actualresult.isEmpty() && actualresult.trim() != "") {

					row++;
					getContent.add(Arrays.asList(Arrays.asList("Environment/Endpoint", env),
							Arrays.asList("TestCaseID", testcasename), Arrays.asList("Request ID", id),
							Arrays.asList("Request Content", toNewFile(request, host, "RequestContent")),
							Arrays.asList("Attachment Content",
									host + File.separator + "files" + File.separator + body),
							Arrays.asList("Expected Response",
									host + File.separator + "files" + File.separator + expectedresult),
							Arrays.asList("Actual Response", toNewFile(actualresult, host, "ActualResponse")),
							Arrays.asList("Status", "PROCESSED"), Arrays.asList("Response Times (ms)", ""),
							Arrays.asList("Comparison of Results", "")));
				}
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();

		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	private String toNewFile(String sContent, String hostname, String fileName) throws IOException {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		final String newFileName = File.separator + "files" + File.separator + fileName + "-" + dateFormat.format(date)
				+ RandomStringUtils.randomAlphabetic(10) + ".xml";
		final BufferedWriter writer = new BufferedWriter(
				new FileWriter(System.getenv("ITS") + File.separator + "FitNesseRoot" + newFileName));
		writer.write(sContent);
		writer.close();
		return hostname + newFileName;
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}
package com.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class PollingDAOImpl implements PollingDao {

	@Override
	public DataPolling queryPolling() throws IOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream(System.getenv("ITS") + File.separator + "jdbc.properties");
		// load a properties file
		prop.load(input);
		System.out.println(prop.getProperty("host"));
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":"
					+ prop.getProperty("port") + "/demo?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tle242@csc.com",
					"root");

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

		DataPolling p = null;
		ResultSet rs = null;
		try {
			// String sql = "SELECT * FROM Polling WHERE id = ?";
			String sql = "SELECT * FROM Polling";
			preparedStatement = connection.prepareStatement(sql);
			// preparedStatement.setString(1, id);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Retrieve by column name
				String id = rs.getString("id");
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
				p = new DataPolling(id, env, host, testcasename, excluding, request, body, actualresult, expectedresult,
						comparision, status, starttime, endtime);
				System.out.println(p.toString());
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return p;

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
		return null;
	}

	private void log(String line) throws ClassNotFoundException, InterruptedException {
		int counter = 100;
		boolean bExit = false;
		while (!bExit && counter > 0) {
			try {
				Files.write(Paths.get(System.getenv("ITS") + File.separator + "log.txt"),
						System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
				Files.write(Paths.get(System.getenv("ITS") + File.separator + "log.txt"), line.getBytes(),
						StandardOpenOption.APPEND);		
				bExit = true;
			} catch (Exception e) {
				Thread.sleep(300);
				counter--;
			}
		}
	}
	
	public void createPolling(DataPolling p) throws IOException, ClassNotFoundException, InterruptedException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream(System.getenv("ITS") + File.separator + "jdbc.properties");
		// load a properties file
		prop.load(input);
		System.out.println(prop.getProperty("host"));
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":"
					+ prop.getProperty("port") + "/demo?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tle242@csc.com",
					"root");

		} catch (ClassNotFoundException | SQLException e) {
			connection = null;
			log("Where is your MySQL JDBC Driver?" + e.toString());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log(e.toString());
				}
			}
		}

		try {
			// connection.setAutoCommit(false);

			StringBuffer sql = new StringBuffer("INSERT INTO Polling (id,request,body,actualresult,expectedresult,");
			sql.append(" comparision, status, starttime, endtime, enviroment, host, testcasename, excluding) VALUES ");
			sql.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			preparedStatement = connection.prepareStatement(sql.toString());

			preparedStatement.setString(1, p.getId());
			preparedStatement.setString(2, p.getRequest());
			preparedStatement.setString(3, p.getBody());
			preparedStatement.setString(4, p.getActualresult());
			preparedStatement.setString(5, p.getExpectedresult());
			preparedStatement.setString(6, p.getComparision());
			preparedStatement.setString(7, p.getStatus());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (p.getStarttime() == null) {
				preparedStatement.setString(8, null);
			} else {
				preparedStatement.setString(8, dateFormat.format(p.getStarttime()));
			}
			if (p.getEndtime() == null) {
				preparedStatement.setString(9, null);
			} else {
				preparedStatement.setString(9, dateFormat.format(p.getEndtime()));
			}
			preparedStatement.setString(10, p.getEnviroment());
			preparedStatement.setString(11, p.getHost());
			preparedStatement.setString(12, p.getTestcasename());
			preparedStatement.setString(13, p.getExcluding());

			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				System.out.println("A new polling was inserted successfully!");
			}

		} catch (SQLException e) {
			log("Connection Failed! Check output console" + e.toString()); 

		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					log(e.toString()); 
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log(e.toString()); 
				}
			}
		}
	}

	public void dropDatabase() throws IOException, ClassNotFoundException, InterruptedException {
		Statement statement = null;
		Connection connection = null;
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream(System.getenv("ITS") + File.separator + "jdbc.properties");
		// load a properties file
		prop.load(input);
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":"
					+ prop.getProperty("port") + "/demo?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tle242@csc.com",
					"root");
			statement = connection.createStatement();

			String sql = "DELETE from Polling";
			statement.executeUpdate(sql);

		} catch (ClassNotFoundException | SQLException e) {
			connection = null;
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log(e.toString()); 
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log(e.toString()); 
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	@Override
	public void updatePolling(String id, DataPolling p) throws IOException, ClassNotFoundException, InterruptedException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream(System.getenv("ITS") + File.separator + "jdbc.properties");
		// load a properties file
		prop.load(input);

		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":"
					+ prop.getProperty("port") + "/demo?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tle242@csc.com",
					"root");

		} catch (ClassNotFoundException | SQLException e) {
			log("Where is your MySQL JDBC Driver?" + e.toString()); 
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log(e.toString()); 
				}
			}
		}

		try {
			StringBuffer sql = new StringBuffer("UPDATE Polling SET actualresult = ?, comparision = ?, status = ?, ");
			sql.append("endtime = ?, excluding = ?, enviroment = ?, host = ?, testcasename = ? WHERE id = ?;");

			preparedStatement = connection.prepareStatement(sql.toString());

			preparedStatement.setString(1, p.getActualresult());
			preparedStatement.setString(2, p.getComparision());
			preparedStatement.setString(3, p.getStatus());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (p.getEndtime() == null) {
				preparedStatement.setString(4, null);
			} else {
				preparedStatement.setString(4, dateFormat.format(p.getEndtime()));
			}
			preparedStatement.setString(5, p.getExcluding());
			preparedStatement.setString(6, p.getEnviroment());
			preparedStatement.setString(7, p.getHost());
			preparedStatement.setString(8, p.getTestcasename());
			preparedStatement.setString(9, p.getId());

			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new polling was updated successfully!");
			}
		} catch (SQLException e) {
			log("Connection Failed! Check output console" + e.toString()); 
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					log(e.toString()); 
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log(e.toString()); 
				}
			}
		}
	}

	@Override

	public void updatePollingActualResult(String id, String actualResult) throws IOException, ClassNotFoundException, InterruptedException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream(System.getenv("ITS") + File.separator + "jdbc.properties");
		// load a properties file
		prop.load(input);

		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":"
					+ prop.getProperty("port") + "/demo?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tle242@csc.com",
					"root");

		} catch (ClassNotFoundException | SQLException e) {
			connection = null;

			log(e.toString()); 
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log(e.toString()); 
				}
			}
		}

		try {
			StringBuffer sql = new StringBuffer("UPDATE Polling SET actualresult = ?, endtime = ? WHERE id = ?;");

			preparedStatement = connection.prepareStatement(sql.toString());

			preparedStatement.setString(1, actualResult);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			preparedStatement.setString(2, dateFormat.format(new Date()));
			preparedStatement.setString(3, id.trim());
			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new polling was updated successfully!");
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

	@Override
	public boolean checkExistPolling(String id) throws IOException, ClassNotFoundException, InterruptedException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream(System.getenv("ITS") + File.separator + "jdbc.properties");
		// load a properties file
		prop.load(input);

		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":"
					+ prop.getProperty("port") + "/demo?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tle242@csc.com",
					"root");

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
		int count = 0;
		try {
			String sql = "SELECT count(*) FROM Polling WHERE id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();

		} catch (SQLException e) {
			log("Connection Failed! Check output console" + e.toString()); 

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
		if (count > 0) {
			System.out.println("A new polling was existed in system.");
			return true;
		}
		return false;
	}

	@Override
	public DataPolling queryPolling(String id) throws IOException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream(System.getenv("ITS") + File.separator + "jdbc.properties");
		// load a properties file
		prop.load(input);
		System.out.println(prop.getProperty("host"));
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":"
					+ prop.getProperty("port") + "/demo?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "tle242@csc.com",
					"root");

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
				p = new DataPolling(id, env, host, testcasename, excluding, request, body, actualresult, expectedresult,
						comparision, status, starttime, endtime);
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return p;

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
		return null;
	}

}

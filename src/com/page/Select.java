package com.page;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Query Table
public class Select {
	private final List<List<List<String>>> getDB;

	public Select(String sql) throws SQLException {
		getDB = new ArrayList<List<List<String>>>();
		// Declare the JDBC objects.
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = getSQLServerConnection_SQLJDBC();
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				switch (sql.split(",").length) {
				case 1: // System.out.println(rs.getString("name"));
					getDB.add(Arrays.asList(Arrays.asList("Col1", rs.getString(1))));
					;
					break;
				case 2: // System.out.println(rs.getString("name"));
					getDB.add(Arrays.asList(Arrays.asList("Col1", rs.getString(1)),
							Arrays.asList("Col2", rs.getString(2))));
					;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
				}
		}
	}

	public List<List<List<String>>> query() {
		return getDB;
	}

	// Connect to SQLServer.
	// (Using SQLJDBC)
	public Connection getSQLServerConnection_SQLJDBC() throws ClassNotFoundException, SQLException {
		String hostName = "13.250.84.21";
		String sqlInstanceName = "SQLEXPRESS";
		String database = "BRI_LIFE_FT_174";
		String userName = "Integral";
		String password = "P@ssword123";

		return getSQLServerConnection_SQLJDBC(hostName, sqlInstanceName, database, userName, password);
	}

	// SQLServer & SQLJDBC.
	private Connection getSQLServerConnection_SQLJDBC(String hostName, String sqlInstanceName, String database,
			String userName, String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionURL = "jdbc:sqlserver://" + hostName + ":1433" + ";instance=" + sqlInstanceName
				+ ";databaseName=" + database;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}
}
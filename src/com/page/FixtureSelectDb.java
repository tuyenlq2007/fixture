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
public class FixtureSelectDb {
	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:tcp://localhost/~/test";
	private static final String DB_USER = "sa";
	private static final String DB_PASSWORD = "";

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (final ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (final SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	private final List<List<List<String>>> getDB;

	public FixtureSelectDb(String sql) throws SQLException {
		getDB = new ArrayList<List<List<String>>>();
		// first row
		// getDB.add(Arrays.asList(Arrays.asList("Name", "tuyenlq")));

		final Connection connection = getDBConnection();
		Statement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			final ResultSet rs = stmt.executeQuery(sql);
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
			stmt.close();
			connection.commit();
		} catch (final SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	// public static void main(String[] args) throws Exception {
	// List<List<String>> a = new ArrayList<List<String>>();
	// a.add(Arrays.asList("Name", "tuyenlq"));
	//
	// }

	public List<List<List<String>>> query() {
		return getDB;
		// return
		// asList( // table level
		// asList( // row level
		// asList("company number", "4808147") // cell column name, value
		// ),
		// asList(
		// asList("company number", "5123122")
		// )
		// );
	}
}
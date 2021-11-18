package performance.ui;

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

	public Select() {
		getDB = new ArrayList<List<List<String>>>();
	}

	public Select(String sql) throws SQLException {
		getDB = new ArrayList<List<List<String>>>();
		// Declare the JDBC objects.
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = getMySqlConnection();
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				switch (sql.split(",").length) {
				
				case 1: 
					getDB.add(Arrays.asList(Arrays.asList("Col1", rs.getString(1))));
					break;
					
				case 2: 
					getDB.add(Arrays.asList(Arrays.asList("Col1", rs.getString(1)),
							Arrays.asList("Col2", rs.getString(2))));
					break;
					
				case 3: 
					getDB.add(Arrays.asList(Arrays.asList("Col1", rs.getString(1)),
							Arrays.asList("Col2", rs.getString(2)), 
									Arrays.asList("Col3", rs.getString(3))));
					break;
					
				case 4: 
					getDB.add(Arrays.asList(Arrays.asList("Col1", rs.getString(1)),
							Arrays.asList("Col2", rs.getString(2)), 
									Arrays.asList("Col3", rs.getString(3)), 
									Arrays.asList("Col4", rs.getString(4))));
				case 5: 
					getDB.add(Arrays.asList(Arrays.asList("STEPID", rs.getString(1)),
							Arrays.asList("USERID", rs.getString(2)), 
									Arrays.asList("STEP_DESCRIPTION", rs.getString(3)), 
									Arrays.asList("TIME", rs.getString(4)), 
									Arrays.asList("ERROR_DESCRIPTION", rs.getString(5))));
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
	public Connection getMySqlConnection() throws ClassNotFoundException, SQLException {
		String hostName = "20.203.133.173";
		String sqlInstanceName = "SQLEXPRESS";
		String database = "testdb";
		String userName = "@tle242";
		String password = "P@ssword123";

		return getMySqlConnection(hostName, sqlInstanceName, database, userName, password);
	}

	// SQLServer & SQLJDBC.
	private Connection getMySqlConnection(String hostName, String sqlInstanceName, String database, String userName,
			String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String connectionURL = "jdbc:mysql://" + hostName + ":3306" + "/" + database;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}
	/*
	 * public void insertDurationTable(String userid, String stepid, String
	 * stepDescription) throws SQLException, ClassNotFoundException { Connection
	 * dbConnection = null; PreparedStatement preparedStatement = null; String
	 * insertTableSQL = "INSERT INTO duration " +
	 * "(USERID,  STEPID,  STEP_DESCRIPTION , TIME ) VALUES" + "(?,?,?,?)"; try
	 * { dbConnection = getMySqlConnection(); preparedStatement =
	 * dbConnection.prepareStatement(insertTableSQL);
	 * preparedStatement.setInt(1, Integer.parseInt(userid));
	 * preparedStatement.setInt(2, Integer.parseInt(stepid));
	 * preparedStatement.setString(3, stepDescription);
	 * preparedStatement.setBigDecimal(4, new BigDecimal(
	 * System.currentTimeMillis())); preparedStatement.executeUpdate(); } catch
	 * (SQLException e) { throw new SQLException(e); } finally { if
	 * (preparedStatement != null) { preparedStatement.close(); } if
	 * (dbConnection != null) { dbConnection.close(); } } }
	 */
}
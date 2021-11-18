package performance.ui;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.configuration.SetUp;

public class Dao {
	public Connection getMySqlConnection() throws ClassNotFoundException, SQLException {
		//String hostName = "20.203.133.180:3306";
		//String sqlInstanceName = "SQLEXPRESS";
		//String database = "testdb";
		//String userName = "tle242";
		//String password = "P@ssword123";

		//return getMySqlConnection(hostName, sqlInstanceName, database, userName, password);
		return getMySqlConnection(SetUp.getSetup().gethostName(), SetUp.getSetup().getsqlInstanceName(), SetUp.getSetup().getdatabase(), SetUp.getSetup().getuserName(), SetUp.getSetup().getpassword());
	}

	// SQLServer & SQLJDBC.
	private Connection getMySqlConnection(String hostName, String sqlInstanceName, String database,
			String userName, String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String connectionURL = "jdbc:mysql://" + hostName + "/" + database;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}

	public void insertDurationTable(String userid, String stepid, String stepDescription, String errorDescription) throws SQLException, ClassNotFoundException {
		Connection dbConnection  = null;
		PreparedStatement preparedStatement = null;
		String insertTableSQL  = "INSERT INTO DURATION "
				+ "(USERID,  STEPID,  STEP_DESCRIPTION , TIME, ERROR_DESCRIPTION ) VALUES"
				+ "(?,?,?,?,?)";
		try {
			dbConnection = getMySqlConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			preparedStatement.setInt(1, Integer.parseInt(userid));
			preparedStatement.setInt(2, Integer.parseInt(stepid));
			preparedStatement.setString(3, stepDescription);
			preparedStatement.setBigDecimal(4, new BigDecimal( System.currentTimeMillis()));
			preparedStatement.setString(5, errorDescription);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	
	public void deleteDurationTable() throws SQLException, ClassNotFoundException {
		Connection dbConnection  = null;
		PreparedStatement preparedStatement = null;
		String deleteTableSQL  = "DELETE FROM DURATION ";
		try {
			dbConnection = getMySqlConnection();
			preparedStatement = dbConnection.prepareStatement(deleteTableSQL);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
}
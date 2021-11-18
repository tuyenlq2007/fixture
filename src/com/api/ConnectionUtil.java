package com.api;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ConnectionUtil {
public Connection getCurrentConnection(){
		
		Connection connection = null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://20.203.6.130:3306/demo?useSSL=false", "root", "root");
			
		} catch (ClassNotFoundException | 
				SQLException  e) {
			connection = null;
			
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		}

		return connection;
	}
	
	public void cleanup(PreparedStatement preparedStatement, Connection connection){
		if (preparedStatement != null){
			try {
				preparedStatement.close();
			} catch (SQLException e) {
			}
		}
		
		if (connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}
	
	
	public void cleanup(ResultSet result, Statement statement, Connection connection){
		if (result != null){
			try {
				result.close();
			} catch (SQLException e) {
			}
		}
		
		if (statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
			}
		}
		
		if (connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}
}

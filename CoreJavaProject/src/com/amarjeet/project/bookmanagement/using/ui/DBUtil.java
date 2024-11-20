package com.amarjeet.project.bookmanagement.using.ui;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String URL = "jdbc:mysql://localhost:3306/library_db"; // Database URL
	private static final String USER = "root"; // MySQL username
	private static final String PASSWORD = "root"; // MySQL password

	public static Connection getConnection() throws SQLException {
		try {
			// Load the MySQL JDBC driver (optional in modern JDBC versions)
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			throw new SQLException("Database connection error", e);
		}
	}
}

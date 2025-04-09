package com.insurance_ms.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DBConnUtil {
	
	    private static Connection connection;

	    public static Connection getConnection() throws ClassNotFoundException {
	        if (connection == null) {
	            try {
	                Properties props = DBPropertyUtil.loadProperties();
	                String url = props.getProperty("db.url");
	                String user = props.getProperty("db.user");
	                String password = props.getProperty("db.password");

	            //    connection = DriverManager.getConnection(,"root", password);
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Insurance", "root", "Raja@2003");
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return connection;
	    }
	}




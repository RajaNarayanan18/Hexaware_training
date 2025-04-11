package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {
	    public static Connection getConnection(Properties props) throws SQLException, ClassNotFoundException {
	    	Class.forName("com.mysql.cj.jdbc.Driver");

	        return DriverManager.getConnection(
	            props.getProperty("db.url"),
	            props.getProperty("db.user"),
	            props.getProperty("db.password")
	        );
	    }
	}



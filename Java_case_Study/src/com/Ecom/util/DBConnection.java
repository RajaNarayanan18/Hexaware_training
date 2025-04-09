package com.Ecom.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection connection;  // STATIC variable

    public static Connection getConnection() {
        if (connection == null) {
            try {
                String connStr = PropertyUtil.getPropertyString("db.properties");
                connection = DriverManager.getConnection(connStr);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}

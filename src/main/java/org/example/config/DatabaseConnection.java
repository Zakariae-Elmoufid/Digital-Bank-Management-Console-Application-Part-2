package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/Digital-Bank"; // fix DB name
    private static final String USER = "postgres";
    private static final String PASSWORD = "123123";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }catch (SQLException e) {
                System.err.println("Connection failed: " + e.getMessage());
            }
        }
        return connection;
    }
}

package com.mishra;

import java.sql.*;

public class DBConnection {

    private Connection connection;
    private static final String URL = "jdbc:mysql://mysql:3306/bankingdb?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "bankinguser";
    private static final String PASSWORD = "bankingpass";

    public DBConnection() {
        connection = null;
    }

    public Connection openConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            connection = null;
        }
        return connection;
    }

    public void closeConn() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.err.println("Can't close the database connection.");
        }
    }
}


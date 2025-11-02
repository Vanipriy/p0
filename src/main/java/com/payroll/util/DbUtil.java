package com.payroll.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/payroll_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // replace with your DB user
    private static final String PASS = "Vanipriya@5"; // replace with your DB password


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
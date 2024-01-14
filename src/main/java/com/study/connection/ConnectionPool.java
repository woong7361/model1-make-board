package com.study.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    static final String DB_URL = "jdbc:mysql://localhost:3306/study";
    static final String USER = "woong";
    static final String PASSWORD = "woong7361";
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(DRIVER);
            try {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();    // 예외 발생시 내용 출력
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Connection Failed. Check Driver or URL");
            e.printStackTrace();        // 예외 발생시 내용 출력
        }

        return connection;
    }

}

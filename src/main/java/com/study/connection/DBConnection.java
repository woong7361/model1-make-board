package com.study.connection;

import com.study.exception.WrapCheckedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.study.config.ConfigConst.*;

/**
 * database 연결과 connection을 관리하는 클래스
 */
public class DBConnection {

    /**
     * Database에서 connection을 가져온다.
     * @return
     */
    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(DRIVER);
            try {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new WrapCheckedException("sql exception", e);
            }
        } catch (ClassNotFoundException e) {
            throw new WrapCheckedException("sql exception", e);
        }

        return connection;
    }

}

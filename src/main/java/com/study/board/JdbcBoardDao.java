package com.study.board;


import com.study.connection.ConnectionPool;
import com.study.encryption.CipherEncrypt;
import com.study.encryption.EncryptManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JdbcBoardDao implements BoardDao{

    public void saveBoard(BoardCreateDto boardCreateDto) throws Exception{
        Connection connection = ConnectionPool.getConnection();
        String createBoardSql = "INSERT INTO board (" +
                "category, name, password, title, " +
                "content, view, created_at, modified_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        EncryptManager encryptManger = new CipherEncrypt();
        String password = encryptManger.encrypt(boardCreateDto.getPassword());

        PreparedStatement preparedStatement = connection.prepareStatement(createBoardSql);
        preparedStatement.setString(1, boardCreateDto.getCategory());
        preparedStatement.setString(2, boardCreateDto.getName());
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, boardCreateDto.getTitle());
        preparedStatement.setString(5, boardCreateDto.getContent());
        preparedStatement.setInt(6, 0);
        preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}

package com.study.comment;

import com.study.comment.dto.CommentCreateDto;
import com.study.connection.ConnectionPool;
import com.study.encryption.CipherEncrypt;
import com.study.encryption.EncryptManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JdbcCommentDao implements CommentDao{
    @Override
    public void saveComment(CommentCreateDto commentCreateDto) throws Exception {
        Connection connection = ConnectionPool.getConnection();

        String createCommentSql = "INSERT INTO comment (" +
                "content, created_at, board_id) " +
                "VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(createCommentSql);
        preparedStatement.setString(1, commentCreateDto.getContent());
        preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setInt(3, commentCreateDto.getBoard_id());
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}

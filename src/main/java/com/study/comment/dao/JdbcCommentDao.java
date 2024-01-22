package com.study.comment.dao;

import com.study.comment.dto.CommentCreateDto;
import com.study.comment.dto.CommentDto;
import com.study.connection.ConnectionPool;
import com.study.exception.WrapCheckedException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcCommentDao implements CommentDao{
    @Override
    public void saveComment(CommentCreateDto commentCreateDto) throws SQLException {
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

    @Override
    public List<CommentDto> getCommentByBoardId(int boardId) {
        String getCommentSql = "SELECT * FROM comment WHERE board_id = ?";

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getCommentSql);
                ) {
            preparedStatement.setInt(1, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<CommentDto> commentList = new ArrayList<>();
            while (resultSet.next()) {
                commentList.add(new CommentDto(
                        resultSet.getInt("comment_id"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                ));
            }

            return commentList;
        } catch (SQLException e) {
            throw new WrapCheckedException("sql Exception", e);
        }
    }


    public void deleteByBoardId(int boardId) {
        String deleteSql = "DELETE FROM comment WHERE board_id = ?";

        try(
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);

                ){
            preparedStatement.setInt(1, boardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new WrapCheckedException("sql Exception", e);
        }
    }

}

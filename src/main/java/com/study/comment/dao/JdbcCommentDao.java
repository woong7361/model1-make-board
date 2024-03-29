package com.study.comment.dao;

import com.study.comment.dto.CommentCreateDto;
import com.study.comment.dto.CommentDto;
import com.study.connection.DBConnection;
import com.study.exception.WrapCheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.study.constant.ExceptionConstant.SQL_EXCEPTION_MESSAGE;

public class JdbcCommentDao implements CommentDao{
    private final Logger logger = LoggerFactory.getLogger(JdbcCommentDao.class);
    @Override
    public void saveComment(CommentCreateDto commentCreateDto) {
        String createCommentSql =
                "INSERT INTO comment " +
                "(content, created_at, board_id) " +
                "VALUES (?, ?, ?)";

        try(
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(createCommentSql);
                ){

            preparedStatement.setString(1, commentCreateDto.getContent());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(3, commentCreateDto.getBoard_id());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("sql: {}",createCommentSql ,e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<CommentDto> getCommentByBoardId(int boardId) {
        String getCommentSql =
                "SELECT * " +
                "FROM comment " +
                "WHERE board_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getCommentSql);
                ) {

            preparedStatement.setInt(1, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<CommentDto> commentList = new ArrayList<>();
            while (resultSet.next()) {
                CommentDto commentDto = CommentDto.builder()
                        .commentId(resultSet.getInt("comment_id"))
                        .content(resultSet.getString("content"))
                        .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                        .build();

                commentList.add(commentDto);
            }

            return commentList;
        } catch (SQLException e) {
            logger.error("sql: {}",getCommentSql ,e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }


    public void deleteByBoardId(int boardId) {
        String deleteSql =
                "DELETE " +
                "FROM comment " +
                "WHERE board_id = ?";

        try(
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);

                ){
            preparedStatement.setInt(1, boardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("sql: {}",deleteSql ,e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

}

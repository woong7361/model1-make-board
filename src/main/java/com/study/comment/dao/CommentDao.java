package com.study.comment.dao;

import com.study.comment.dto.CommentCreateDto;
import com.study.comment.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

public interface CommentDao {
    void saveComment(CommentCreateDto commentCreateDto) throws SQLException;

    List<CommentDto> getCommentByBoardId(int boardId) throws SQLException;

    void deleteByBoardId(int boardId) throws SQLException;
}

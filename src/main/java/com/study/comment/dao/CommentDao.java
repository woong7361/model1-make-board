package com.study.comment.dao;

import com.study.comment.dto.CommentCreateDto;
import com.study.comment.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

public interface CommentDao {
    void saveComment(CommentCreateDto commentCreateDto);

    List<CommentDto> getCommentByBoardId(int boardId);

    void deleteByBoardId(int boardId);
}

package com.study.comment.dao;

import com.study.comment.dto.CommentCreateDto;
import com.study.comment.dto.CommentDto;

import java.util.List;

public interface CommentDao {
    void saveComment(CommentCreateDto commentCreateDto) throws Exception;

    List<CommentDto> getCommentByBoardId(int boardId) throws Exception;

    void deleteByBoardId(int boardId) throws Exception;
}

package com.study.comment.dao;

import com.study.comment.dto.CommentCreateDto;
import com.study.comment.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

/**
 * comment data access object
 */
public interface CommentDao {

    /**
     * 저장공간에 comment를 저장한다.
     * @param commentCreateDto
     */
    void saveComment(CommentCreateDto commentCreateDto);

    /**
     * board에 연결되어있는 comment를 모두 가져온다.
     * @param boardId
     * @return boardId에 연결된 comment list를 반환.
     */
    List<CommentDto> getCommentByBoardId(int boardId);

    /**
     * board에 연결된 comment들을 삭제한다.
     * @param boardId
     */
    void deleteByBoardId(int boardId);
}

package com.study.comment;

import com.study.comment.dto.CommentCreateDto;

public interface CommentDao {
    void saveComment(CommentCreateDto commentCreateDto) throws Exception;

}

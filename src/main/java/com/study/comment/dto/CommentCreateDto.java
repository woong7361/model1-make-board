package com.study.comment.dto;

import javax.servlet.http.HttpServletRequest;

public class CommentCreateDto {
    final int board_id;
    final String content;

    public CommentCreateDto(int board_id, String content) {
        this.board_id = board_id;
        this.content = content;
    }

    public int getBoard_id() {
        return board_id;
    }

    public String getContent() {
        return content;
    }
}

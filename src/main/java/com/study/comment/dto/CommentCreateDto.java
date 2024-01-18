package com.study.comment.dto;

import javax.servlet.http.HttpServletRequest;

public class CommentCreateDto {
    final int board_id;
    final String content;

    public CommentCreateDto(HttpServletRequest request) {
        this.board_id = Integer.parseInt(request.getParameter("board_id"));
        this.content = request.getParameter("content");
    }

    public int getBoard_id() {
        return board_id;
    }

    public String getContent() {
        return content;
    }
}

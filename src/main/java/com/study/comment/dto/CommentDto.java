package com.study.comment.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentDto {
    private final int commentId;
    private final String content;
    private final LocalDateTime createdAt;

    public CommentDto(int commentId, String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAtString() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

package com.study.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class CommentDto {
    private final int commentId;
    private final String content;
    private final LocalDateTime createdAt;
}

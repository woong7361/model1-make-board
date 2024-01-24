package com.study.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * comment view를 위한 dto
 */
@Getter
@Builder
public class CommentDto {
    private final int commentId;
    private final String content;
    private final LocalDateTime createdAt;
}

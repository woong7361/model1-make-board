package com.study.comment.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * comment create를 위한 dto
 */
@Getter
@Builder
public class CommentCreateDto {
    final int board_id;
    final String content;
}

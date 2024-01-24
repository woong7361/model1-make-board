package com.study.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCreateDto {
    final int board_id;
    final String content;
}

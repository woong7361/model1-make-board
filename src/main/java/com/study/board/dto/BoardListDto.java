package com.study.board.dto;

import com.study.board.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * board list view를 위한 dto
 */
@Getter
@Builder
public class BoardListDto {
    private final int boardId;
    private final Category category;
    private final String title;
    private final String name;
    private final int view;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final boolean havaFile;
}

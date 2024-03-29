package com.study.board.dto;

import com.study.board.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * board search를 위한 dto
 */
@Getter
@Builder
public class BoardSearchDto {
    private final String searchKey;
    private final Category searchCategory;
    private final LocalDateTime searchStartDate;
    private final LocalDateTime searchEndDate;
}

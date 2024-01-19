package com.study.board.dto;

import com.study.board.Category;

import java.time.LocalDateTime;

public class BoardSearchDto {
    private final String searchKey;
    private final Category searchCategory;
    private final LocalDateTime searchStartDate;
    private final LocalDateTime searchEndDate;

    public BoardSearchDto(String searchKey, Category searchCategory, LocalDateTime searchStartDate, LocalDateTime searchEndDate) {
        this.searchKey = searchKey;
        this.searchCategory = searchCategory;
        this.searchStartDate = searchStartDate;
        this.searchEndDate = searchEndDate;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public Category getSearchCategory() {
        return searchCategory;
    }

    public LocalDateTime getSearchStartDate() {
        return searchStartDate;
    }

    public LocalDateTime getSearchEndDate() {
        return searchEndDate;
    }
}

package com.study.board.dto;

import com.study.board.Category;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BoardListDto {
    private final int boardId;
    private final Category category;
    private final String title;
    private final String name;
    private final int view;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final boolean havaFile;

    public BoardListDto(int boardId, Category category, String title, String name, int view, LocalDateTime createdAt, LocalDateTime modifiedAt, boolean havaFile) {
        this.boardId = boardId;
        this.category = category;
        this.title = title;
        this.name = name;
        this.view = view;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.havaFile = havaFile;
    }

    public int getBoardId() {
        return boardId;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public int getView() {
        return view;
    }

    public String getCreatedAtString() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getModifiedAtString() {
        return modifiedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public boolean isHavaFile() {
        return havaFile;
    }
}

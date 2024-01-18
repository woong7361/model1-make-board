package com.study.board.dto;

import com.study.board.Category;
import com.study.comment.dto.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {
    private final int boardId;
    private final Category category;
    private final String title;
    private final String name;
    private final String content;
    private final int view;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public BoardDto(int boardId, Category category, String title, String name, String content, int view, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.boardId = boardId;
        this.category = category;
        this.title = title;
        this.name = name;
        this.content = content;
        this.view = view;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
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

    public String getContent() {
        return content;
    }

    public int getView() {
        return view;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
}

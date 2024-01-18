package com.study.board.dto;

import com.study.file.FileCreateDto;

import java.util.List;

public class BoardCreateDto {
    private final String category;
    private final String name;
    private final String password;
    private final String title;
    private final String content;
    private final List<FileCreateDto> fileList;

    public BoardCreateDto(String category, String name, String password, String title, String content, List<FileCreateDto> fileList) {
        this.category = category;
        this.name = name;
        this.password = password;
        this.title = title;
        this.content = content;
        this.fileList = fileList;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<FileCreateDto> getFileList() {
        return fileList;
    }
}

package com.study.board;

public class BoardCreateDto {
    private final String category;
    private final String name;
    private final String password;
    private final String title;
    private final String content;
    private final String file;

    public BoardCreateDto(String category, String name, String password, String title, String content, String file) {
        this.category = category;
        this.name = name;
        this.password = password;
        this.title = title;
        this.content = content;
        this.file = file;
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
}

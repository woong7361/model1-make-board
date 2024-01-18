package com.study.board;

public enum Category {
    ALL(0),
    JAVA(1),
    JAVASCRIPT(2),
    DATABASE(3);

    private int categoryId;

    private Category(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
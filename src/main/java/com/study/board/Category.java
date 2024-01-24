package com.study.board;

import lombok.Getter;

/**
 * 게시글 카테고리 클래스
 * <p>
 *     DB category_id에 해당하는 categoryId를 가지고있다.
 * </p>
 */
@Getter
public enum Category {
    ALL(0),
    JAVA(1),
    JAVASCRIPT(2),
    DATABASE(3);

    private final int categoryId;

    private Category(int categoryId) {
        this.categoryId = categoryId;
    }
}
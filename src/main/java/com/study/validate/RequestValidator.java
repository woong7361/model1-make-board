package com.study.validate;

import com.oreilly.servlet.MultipartRequest;
import com.study.board.Category;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

public class RequestValidator {
    static final String CATEGORY_PARAM = "category";
    static final String PASSWORD_PARAM = "password";
    static final String NAME_PARAM = "name";
    static final String TITLE_PARAM = "title";
    static final String BOARD_CONTENT_PARAM = "content";
    static final String COMMENT_CONTENT_PARAM = "content";
    static final String BOARD_ID_PARAM = "board_id";


    static final String NAME_PATTERN = "^[가-힣]{3,4}$";
    static final String PASSWORD_PATTERN = "^[a-zA-Z0-9#?!@$%^&*-]{4,15}$";
    static final String TITLE_PATTERN = ".{4,100}$";
    static final String CONTENT_PATTERN = ".{4,2000}$";
    static final String PK_PATTERN = "^[0-9]+$";

    public void validateCreateBoardRequest(MultipartRequest multipartRequest) {
        this.validateBoardCategory(multipartRequest.getParameter(CATEGORY_PARAM));
        this.validateParameterPattern(multipartRequest.getParameter(NAME_PARAM), NAME_PATTERN);
        this.validateParameterPattern(multipartRequest.getParameter(PASSWORD_PARAM), PASSWORD_PATTERN);
        this.validateParameterPattern(multipartRequest.getParameter(TITLE_PARAM), TITLE_PATTERN);
        this.validateParameterPattern(multipartRequest.getParameter(BOARD_CONTENT_PARAM), CONTENT_PATTERN);
    }

    public void validateCreateComment(HttpServletRequest request) {
        this.validateParameterPattern(request.getParameter(BOARD_ID_PARAM), PK_PATTERN);
        this.validateParameterPattern(request.getParameter(COMMENT_CONTENT_PARAM), CONTENT_PATTERN);

    }

    // error 설명이 매우 약함
    private void validateParameterPattern(String str, String pattern) {
        Optional
                .ofNullable(str)
                .filter((s) -> Pattern.matches(pattern, s))
                .orElseThrow(() -> new IllegalArgumentException("invalid request param"));
    }

    private void validateBoardCategory(String category) {
        Category[] categories = Category.values();

        boolean match = Arrays.stream(categories)
                .map(c -> c.toString())
                .anyMatch(c -> c.equals(category));

        if (!match) {
            throw new IllegalArgumentException("invalid category param");
        }
    }

}

package com.study.filter;

import com.oreilly.servlet.MultipartRequest;
import com.study.board.Category;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.study.constant.RequestParamConstant.*;

/**
 * 정규표현식을 이용해 pattern을 검증한다.
 */
public class PatternValidator {
    static final String NAME_PATTERN = "^[가-힣]{3,4}$";
    static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{4,15}$";
    static final String TITLE_PATTERN = ".{4,100}$";
    static final String CONTENT_PATTERN = ".{4,2000}$";
    static final String PK_PATTERN = "^[0-9]+$";

    public void validateParameterPattern(String str, String pattern) {
        Optional
                .ofNullable(str)
                .filter((s) -> Pattern.matches(pattern, s))
                .orElseThrow(() -> new IllegalArgumentException("invalid request param"));
    }

    public void validateBoardCategory(String category) {
        Category[] categories = Category.values();

        boolean match = Arrays.stream(categories)
                .map(c -> c.name())
                .anyMatch(c -> c.equals(category));

        if (!match) {
            throw new IllegalArgumentException("invalid category param");
        }
    }

}

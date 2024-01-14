package com.study.board;

import java.util.Arrays;
import java.util.regex.Pattern;

public class ValidateBoard {

    public static boolean validateCreateBoard(BoardCreateDto boardCreateDto) {
        Category[] categories = Category.values();
        String namePattern = "^[가-힣]{3,4}$"; // 숫자만 등장하는지
        String passwordPattern = "^[a-zA-Z0-9#?!@$%^&*-]{4,15}$"; // 숫자만 등장하는지
        String titlePattern = ".{4,100}$"; // 숫자만 등장하는지
        String contentPattern = ".{4,2000}$"; // 숫자만 등장하는지

        boolean categoryMatchResult = Arrays.stream(categories)
                .map(c -> c.toString()).anyMatch(c -> c.equals(boardCreateDto.getCategory()));
        boolean nameMatchResult = Pattern.matches(namePattern, boardCreateDto.getName());
        boolean passwordMatchResult = Pattern.matches(passwordPattern, boardCreateDto.getPassword());
        boolean titleMatchResult = Pattern.matches(titlePattern, boardCreateDto.getTitle());
        boolean contentMatchResult = Pattern.matches(contentPattern, boardCreateDto.getContent());

        return categoryMatchResult && nameMatchResult && passwordMatchResult && titleMatchResult && contentMatchResult;
    }
}

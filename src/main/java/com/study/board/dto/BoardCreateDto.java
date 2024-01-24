package com.study.board.dto;

import com.study.board.Category;
import com.study.file.dto.FileCreateDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * board 생성을 위한 dto
 */
@Getter
@Builder
public class BoardCreateDto {
    private final Category category;
    private final String name;
    private final String password;
    private final String title;
    private final String content;
    private final List<FileCreateDto> fileList;
}

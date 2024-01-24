package com.study.board.dto;

import com.study.file.dto.FileCreateDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * board update를 위한 dto
 */
@Getter
@Builder
public class BoardModifyDto {
    private final int board_id;
    private final String name;
    private final String password;
    private final String title;
    private final String content;
    private final List<FileCreateDto> createFileList;
    private final List<Integer> deleteFileIdList;
}

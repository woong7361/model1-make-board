package com.study.file.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * file 생성을 위한 dto
 */
@Getter
@Builder
public class FileCreateDto {
    private final String originalFileName;
    private final String fileName;
    private final String filePath;
    private final String extension;
}

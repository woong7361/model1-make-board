package com.study.file.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileCreateDto {
    private final String originalFileName;
    private final String fileName;
    private final String filePath;
    private final String extension;
}

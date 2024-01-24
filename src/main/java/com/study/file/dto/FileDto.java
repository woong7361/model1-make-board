package com.study.file.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileDto {
    private final int fileId;
    private final String originalName;
}

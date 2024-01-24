package com.study.file.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * file view를 위한 dto
 */
@Getter
@Builder
public class FileDto {
    private final int fileId;
    private final String originalName;
}

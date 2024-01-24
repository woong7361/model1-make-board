package com.study.file.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * file download를 위한 dto
 */
@Getter
@Builder
public class FileDownloadDto {
    private final String originalName;
    private final String name;
    private final String path;
}

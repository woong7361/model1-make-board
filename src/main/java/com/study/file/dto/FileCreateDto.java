package com.study.file.dto;

import java.io.File;

public class FileCreateDto {
    private final String originalFileName;
    private final String fileName;
    private final String filePath;
    private final String extension;

    public FileCreateDto(String originalFileName, String fileName, String filePath, String extension) {
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.extension = extension;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getExtension() {
        return extension;
    }

    public String getFilePath() {
        return filePath;
    }
}

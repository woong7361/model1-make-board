package com.study.file;

public class FileDto {
    private final int fileId;
    private final String originalName;

    public FileDto(int fileId, String originalName) {
        this.fileId = fileId;
        this.originalName = originalName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public int getFileId() {
        return fileId;
    }
}

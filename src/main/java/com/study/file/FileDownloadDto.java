package com.study.file;

public class FileDownloadDto {
    private final String originalName;
    private final String name;
    private final String path;

    public FileDownloadDto(String originalName, String name, String path) {
        this.originalName = originalName;
        this.name = name;
        this.path = path;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}

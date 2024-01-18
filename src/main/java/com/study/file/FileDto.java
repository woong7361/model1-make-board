package com.study.file;

public class FileDto {
    final String originalName;
    final String Name;
    final String path;

    public FileDto(String originalName, String name, String path) {
        this.originalName = originalName;
        Name = name;
        this.path = path;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getName() {
        return Name;
    }

    public String getPath() {
        return path;
    }
}

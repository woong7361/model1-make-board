package com.study.board.dto;

import com.study.file.FileCreateDto;

import java.util.List;

public class BoardModifyDto {
    private final int board_id;
    private final String name;
    private final String password;
    private final String title;
    private final String content;
    private final List<FileCreateDto> createFileList;
    private final List<Integer> deleteFileIdList;

    public BoardModifyDto(int boardId, String name, String password, String title, String content, List<FileCreateDto> createFileList, List<Integer> deleteFileIdList) {
        this.board_id = boardId;
        this.name = name;
        this.password = password;
        this.title = title;
        this.content = content;
        this.createFileList = createFileList;
        this.deleteFileIdList = deleteFileIdList;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<FileCreateDto> getCreateFileList() {
        return createFileList;
    }

    public List<Integer> getDeleteFileIdList() {
        return deleteFileIdList;
    }

    public int getBoard_id() {
        return board_id;
    }
}

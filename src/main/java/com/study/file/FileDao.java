package com.study.file;

import java.util.List;

public interface FileDao {

    void saveFileList(List<FileCreateDto> fileList, int boardId) throws Exception;

    List<FileDto> getFileByBoardId(int boardId) throws Exception;
}

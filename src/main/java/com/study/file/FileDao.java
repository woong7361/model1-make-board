package com.study.file;

import java.util.List;
import java.util.Optional;

public interface FileDao {

    void saveFileList(List<FileCreateDto> fileList, int boardId) throws Exception;

    List<FileDto> getFileByBoardId(int boardId) throws Exception;

    Optional<FileDownloadDto> getFileByFileId(int fileId) throws Exception;

    void deleteFileList(List<Integer> deleteFileIdList) throws Exception;

    void deleteByBoardId(int boardId) throws Exception;

    List<String> getFilePathListByBoardId(int boardId) throws Exception;
}

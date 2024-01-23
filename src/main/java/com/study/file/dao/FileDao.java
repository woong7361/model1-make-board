package com.study.file.dao;

import com.study.file.dto.FileCreateDto;
import com.study.file.dto.FileDownloadDto;
import com.study.file.dto.FileDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FileDao {

    void saveFileListIdList(List<FileCreateDto> fileList, int boardId);

    List<FileDto> getFileByBoardId(int boardId);

    Optional<FileDownloadDto> getFileByFileId(int fileId);

    void deleteFileByListIdList(List<Integer> deleteFileIdList);

    void deleteByBoardId(int boardId);

    List<String> getFilePathListByBoardId(int boardId);

    List<String> getFilePathListByIdList(List<Integer> FileIdList);
}

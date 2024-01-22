package com.study.file.dao;

import com.study.file.dto.FileCreateDto;
import com.study.file.dto.FileDownloadDto;
import com.study.file.dto.FileDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FileDao {

    void saveFileListIdList(List<FileCreateDto> fileList, int boardId) throws SQLException;

    List<FileDto> getFileByBoardId(int boardId) throws SQLException;

    Optional<FileDownloadDto> getFileByFileId(int fileId) throws SQLException;

    void deleteFileByListIdList(List<Integer> deleteFileIdList) throws SQLException;

    void deleteByBoardId(int boardId) throws SQLException;

    List<String> getFilePathListByBoardId(int boardId) throws SQLException;

    List<String> getFilePathListByIdList(List<Integer> FileIdList) throws SQLException;
}

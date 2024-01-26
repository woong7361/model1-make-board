package com.study.file.dao;

import com.study.file.dto.FileCreateDto;
import com.study.file.dto.FileDownloadDto;
import com.study.file.dto.FileDto;

import java.util.List;
import java.util.Optional;

/**
 * file data access object
 */
public interface FileDao {

    /**
     * 저장공간에 file list 저장
     * @param fileList 저장하려는 파일 dto
     * @param boardId 파일의 주인 boardId
     */
    void saveFileList(List<FileCreateDto> fileList, int boardId);

    /**
     * boardId에 연결되있는 파일들을 가져온다.
     * @param boardId 파일의 주인 boardId
     * @return file dto list를 반환한다.
     */
    List<FileDto> getFileDtoByBoardId(int boardId);

    /**
     * fileId를 통해 file을 가져온다.
     * @param fileId file's ID
     * @return fileDownload에 필요한 file dto를 반환한다.
     */
    Optional<FileDownloadDto> getFileDownloadDtoByFileId(int fileId);

    /**
     * 삭제할 파일의 id list를 받아 저장공간에서 삭제한다.
     * @param deleteFileIdList 삭제할 file의 id list
     */
    void deleteFileByListIdList(List<Integer> deleteFileIdList);

    /**
     * boarId와 연관되어있는 파일들을 모두 삭제한다.
     * @param boardId board's id
     */
    void deleteByBoardId(int boardId);

    /**
     * boardId와 연관되어있는 파일들의 path list를 가져온다.
     * @param boardId board's id
     * @return file이 저장되어있는 경로 list
     */
    List<String> getFilePathListByBoardId(int boardId);

    /**
     * file id list들에 해당하는 file의 path list를 가져온다.
     * @param FileIdList file's id list
     * @return file's path list
     */
    List<String> getFileFullPathListByIdList(List<Integer> FileIdList);
}

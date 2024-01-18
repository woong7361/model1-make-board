package com.study.multipart;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import com.study.board.Category;
import com.study.board.dto.BoardCreateDto;
import com.study.board.dto.BoardModifyDto;
import com.study.file.FileCreateDto;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultipartHandler {

    public static final String SAVE_DIR = "/files";
    public static final int MAX_FILE_SIZE = 1024 * 1024 * 5;
    public static final String UTF_8_ENCODE_TYPE = "UTF-8";

    public BoardCreateDto getBoardCreateDto(MultipartRequest multipartRequest) {
        List<FileCreateDto> fileList = this.getFileCreateDtoList(multipartRequest);

        return new BoardCreateDto(
                Category.valueOf(multipartRequest.getParameter("category")),
                multipartRequest.getParameter("name"),
                multipartRequest.getParameter("password"),
                multipartRequest.getParameter("title"),
                multipartRequest.getParameter("content"),
                fileList
        );
    }

    public MultipartRequest getMultipartRequest(HttpServletRequest request) {
        String saveDirectoryPath = request.getSession().getServletContext().getRealPath(SAVE_DIR);
        FileRenamePolicy policy = new UniqueFileNamePolicy();

        try {
            return new MultipartRequest(request, saveDirectoryPath, MAX_FILE_SIZE, UTF_8_ENCODE_TYPE, policy);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("MULTIPART IO EXCEPTION");
        }
    }

    public BoardModifyDto getBoardModifyDto(MultipartRequest multipartRequest) {
        List<FileCreateDto> fileCreateList = this.getFileCreateDtoList(multipartRequest);
        List<Integer> deleteFileIdList = getFileDeleteIdList(multipartRequest);

        return new BoardModifyDto(
                Integer.parseInt(multipartRequest.getParameter("board_id")),
                multipartRequest.getParameter("name"),
                multipartRequest.getParameter("password"),
                multipartRequest.getParameter("title"),
                multipartRequest.getParameter("content"),
                fileCreateList,
                deleteFileIdList
        );
    }

    private List<FileCreateDto> getFileCreateDtoList(MultipartRequest multipartRequest) {
        List<String> fileCreateParamList = new ArrayList<>();
        fileCreateParamList.add("file_add1");
        fileCreateParamList.add("file_add2");
        fileCreateParamList.add("file_add3");

        List<String> existFileParamList = fileCreateParamList.stream()
                .filter((param) -> Optional.ofNullable(multipartRequest.getFile(param)).isPresent())
                .collect(Collectors.toList());

        List<FileCreateDto> fileList = new ArrayList<>();
        for (String fileParam : existFileParamList) {
            File file = multipartRequest.getFile(fileParam);
            String[] nameSplitList = file.getName().split("[.]");
            String extension = nameSplitList[nameSplitList.length - 1];

            fileList.add(new FileCreateDto(
                    multipartRequest.getOriginalFileName(fileParam),
                    file.getName(),
                    file.getAbsolutePath(),
                    extension
            ));
        }

        return fileList;
    }

    private List<Integer> getFileDeleteIdList(MultipartRequest multipartRequest) {
        List<String> fileDeleteParamList = new ArrayList<>();
        fileDeleteParamList.add("file_delete1");
        fileDeleteParamList.add("file_delete2");
        fileDeleteParamList.add("file_delete3");

        List<Integer> deleteFileIdList = fileDeleteParamList.stream()
                .map((param) -> multipartRequest.getParameter(param))
                .filter((id) -> Optional.ofNullable(id).isPresent())
                .map((id) -> Integer.valueOf(id))
                .collect(Collectors.toList());

        return deleteFileIdList;
    }
}

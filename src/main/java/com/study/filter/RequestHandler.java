package com.study.filter;

import com.oreilly.servlet.MultipartRequest;
import com.study.board.Category;
import com.study.board.dto.BoardCreateDto;
import com.study.board.dto.BoardModifyDto;
import com.study.board.dto.BoardSearchDto;
import com.study.comment.dto.CommentCreateDto;
import com.study.file.dto.FileCreateDto;
import com.study.filter.multitpart.MultipartHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.study.constant.ExceptionConstant.INVALID_REQUEST_PARAM_MESSAGE;
import static com.study.constant.RequestParamConstant.*;

/**
 * servlet 이후 HTTP request의 전처리를 담당한다.
 *
 * <p>검증, multipart handling, deserialization 담당한다.</p>
 */
public class RequestHandler {
    private static final int FIRST_PAGE = 0;

    private final MultipartHandler multipartHandler = new MultipartHandler();
    private final Validator validator = new Validator();


    /**
     * request param에서 boardSearchDto를 가져온다.
     * @param request http request
     * @return 검색조건이 있다면 반환하고 없다면 기본값을 반환한다.
     */
    public BoardSearchDto getBoardSearchDto(HttpServletRequest request) {
        return BoardSearchDto.builder()
                .searchKey(getStringParameterWithDefaultValue(request, SEARCH_KEY_PARAM, FIND_ALL))
                .searchCategory(getCategoryParameterWithDefaultValue(request, SEARCH_CATEGORY_PARAM, Category.ALL))
                .searchStartDate(getLocalDateTimeParameterByDateTimeWithDefaultValue(request, SEARCH_START_DATE_PARAM, LocalDateTime.now().minusYears(1L)))
                .searchEndDate(getLocalDateTimeParameterByDateTimeWithDefaultValue(request, SEARCH_END_DATE_PARAM, LocalDateTime.now()))
                .build();
    }

    /**
     * request param에서 현재 페이지를 가져온다.
     * @param request http request
     * @return parma이 있다면 현제패이지를 반환하고, 없다면 첫페이지를 반환한다.
     */
    public int getCurrentPage(HttpServletRequest request) {
        return getIntParameterWithDefaultValue(request, PAGE_PARAM, FIRST_PAGE);
    }


    /**
     * request param에서 BoardCreateDto를 가져온다.
     * @param request http request
     * @return board를 만들때 필요한 dto를 반환한다.
     */
    public BoardCreateDto getBoardCreateDto(HttpServletRequest request) {
        MultipartRequest multipartRequest = multipartHandler.getMultipartRequest(request);

        validator.validateCreateBoardRequest(multipartRequest);

        List<FileCreateDto> fileList = this.getFileCreateDtoList(multipartRequest);

        return BoardCreateDto.builder()
                .category(Category.valueOf(multipartRequest.getParameter(CATEGORY_PARAM)))
                .name(multipartRequest.getParameter(NAME_PARAM))
                .password(multipartRequest.getParameter(PASSWORD_PARAM))
                .title(multipartRequest.getParameter(TITLE_PARAM))
                .content(multipartRequest.getParameter(CONTENT_PARAM))
                .fileList(fileList)
                .build();
    }

    /**
     * request param에서 boardModigyDto를 가져온다.
     * @param request http request
     * @return board update에 필요한 dto를 반환한다.
     */
    public BoardModifyDto getBoardModifyDto(HttpServletRequest request) {
        MultipartRequest multipartRequest = multipartHandler.getMultipartRequest(request);

        validator.validateModifyBoardRequest(multipartRequest);

        List<FileCreateDto> fileCreateList = this.getFileCreateDtoList(multipartRequest);
        List<Integer> deleteFileIdList = getFileDeleteIdList(multipartRequest);

        return BoardModifyDto.builder()
                .board_id(Integer.parseInt(multipartRequest.getParameter(BOARD_ID_PARAM)))
                .name(multipartRequest.getParameter(NAME_PARAM))
                .password(multipartRequest.getParameter(PASSWORD_PARAM))
                .title(multipartRequest.getParameter(TITLE_PARAM))
                .content(multipartRequest.getParameter(CONTENT_PARAM))
                .createFileList(fileCreateList)
                .deleteFileIdList(deleteFileIdList)
                .build();
    }


    /**
     * request param에서 CommentCreateDto를 가져온다.
     * @param request http request
     * @return comment create에 필요한 dto를 반환한다.
     */
    public CommentCreateDto getCommentCreateDto(HttpServletRequest request) {
        validator.validateCreateComment(request);

        return CommentCreateDto.builder()
                .board_id(Integer.parseInt(request.getParameter(BOARD_ID_PARAM)))
                .content(request.getParameter(CONTENT_PARAM))
                .build();
    }

    /**
     * request param에서 BoardId를 가져온다.
     * @param request http request
     * @return board id
     */
    public int getBoardId(HttpServletRequest request) {
        return getIntParameterOrElseThrow(request, BOARD_ID_PARAM);
    }

    /**
     * request param에서 FileId를 가져온다.
     * @param request http request
     * @return file id
     */
    public int getFileId(HttpServletRequest request) {
        return getIntParameterOrElseThrow(request, FILE_ID_PARAM);
    }

//    ------------------------------------------------------------------------------------------------------------------

    private String getStringParameterWithDefaultValue(HttpServletRequest request, String parameter, String defaultValue) {
        return Optional.ofNullable(request.getParameter(parameter))
                .orElse(defaultValue);
    }
    private Category getCategoryParameterWithDefaultValue(HttpServletRequest request, String parameter, Category defaultValue) {
        return Optional.ofNullable(request.getParameter(parameter))
                .map((p) -> Category.valueOf(p))
                .orElse(defaultValue);
    }
    private int getIntParameterWithDefaultValue(HttpServletRequest request, String parameter, int defaultValue) {
        return Optional.ofNullable(request.getParameter(parameter))
                .map((p) -> Integer.parseInt(p))
                .orElse(defaultValue);
    }

    private LocalDateTime getLocalDateTimeParameterByDateTimeWithDefaultValue(HttpServletRequest request, String parameter, LocalDateTime defaultValue) {
        return Optional.ofNullable(request.getParameter(parameter))
                .map((p) -> LocalDateTime.of(LocalDate.parse(p), LocalTime.MIN))
                .orElse(defaultValue);
    }

    private int getIntParameterOrElseThrow(HttpServletRequest request, String parameter) {
        return Optional.ofNullable(request.getParameter(parameter))
                .map((p) -> Integer.parseInt(p))
                .orElseThrow(() -> new IllegalArgumentException(INVALID_REQUEST_PARAM_MESSAGE));
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
            String extension = getExtension(file);

            FileCreateDto fileCreateDto = FileCreateDto.builder()
                    .originalFileName(multipartRequest.getOriginalFileName(fileParam))
                    .fileName(file.getName())
                    .filePath(getFilePath(file))
                    .extension(extension)
                    .build();

            fileList.add(fileCreateDto);
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

    private String getExtension(File file) {
        String[] nameSplitList = file.getName().split("[.]");

        String extension = nameSplitList[nameSplitList.length - 1];
        return extension;
    }

    private String getFilePath(File file) {
        String[] nameSplitList = file.getAbsolutePath().split("[/]");
        List<String> list = new ArrayList<>(Arrays.asList(nameSplitList));
        list.remove(list.size() - 1);

        String name = String.join("/", list);
        return name;
    }

}

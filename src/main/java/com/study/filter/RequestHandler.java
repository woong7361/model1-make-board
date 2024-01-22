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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestHandler {
    private static final int FIRST_PAGE = 0;
    private static final String SEARCH_KEY_PARAM = "search_key";
    private static final String SEARCH_END_DATE_PARAM = "end_date";
    private static final String SEARCH_START_DATE_PARAM = "start_date";
    private static final String SEARCH_CATEGORY_PARAM = "search_category";


    private static final String PAGE_PARAM = "page";
    private static final String FIND_ALL = "ALL";
    private static final String CATEGORY_PARAM = "category";
    private static final String NAME_PARAM = "name";
    private static final String PASSWORD_PARAM = "password";
    private static final String TITLE_PARAM = "title";
    private static final String CONTENT_PARAM = "content";
    private static final String BOARD_ID_PARAM = "board_id";
//    private static final List<String> FILE_CREATE_PARAM_LIST = Arrays.asList("file_add1", "file_add2", "file_add3");
//    private static final List<String> FILE_DELETE_PARAM_LIST = Arrays.asList("file_delete1", "file_delete2", "file_delete3");


    private final MultipartHandler multipartHandler = new MultipartHandler();
    private static PatternValidator patternValidator = new PatternValidator();

    public BoardSearchDto getBoardSearchDto(HttpServletRequest request) {
         return new BoardSearchDto(
                getStringParameterWithDefaultValue(request, SEARCH_KEY_PARAM, FIND_ALL),
                getCategoryParameterWithDefaultValue(request, SEARCH_CATEGORY_PARAM, Category.ALL),
                getLocalDateTimeParameterByDateTimeWithDefaultValue(request, SEARCH_START_DATE_PARAM, LocalDateTime.now().minusYears(1L)),
                getLocalDateTimeParameterByDateTimeWithDefaultValue(request, SEARCH_END_DATE_PARAM, LocalDateTime.now())
        );
    }

    public int getCurrentPage(HttpServletRequest request) {
        return getIntParameterWithDefaultValue(request, PAGE_PARAM, FIRST_PAGE);
    }


    public BoardCreateDto getBoardCreateDto(MultipartRequest multipartRequest) {
        patternValidator.validateCreateBoardRequest(multipartRequest);

        List<FileCreateDto> fileList = this.getFileCreateDtoList(multipartRequest);

        return new BoardCreateDto(
                Category.valueOf(multipartRequest.getParameter(CATEGORY_PARAM)),
                multipartRequest.getParameter(NAME_PARAM),
                multipartRequest.getParameter(PASSWORD_PARAM),
                multipartRequest.getParameter(TITLE_PARAM),
                multipartRequest.getParameter(CONTENT_PARAM),
                fileList
        );
    }

    /**
     * get BoardCreateDto By Request
     *
     * @param request
     * @return
     */
    public BoardCreateDto getBoardCreateDto(HttpServletRequest request) {
        MultipartRequest multipartRequest = multipartHandler.getMultipartRequest(request);

        patternValidator.validateCreateBoardRequest(multipartRequest);

        List<FileCreateDto> fileList = this.getFileCreateDtoList(multipartRequest);

        return new BoardCreateDto(
                Category.valueOf(multipartRequest.getParameter(CATEGORY_PARAM)),
                multipartRequest.getParameter(NAME_PARAM),
                multipartRequest.getParameter(PASSWORD_PARAM),
                multipartRequest.getParameter(TITLE_PARAM),
                multipartRequest.getParameter(CONTENT_PARAM),
                fileList
        );
    }

    public BoardModifyDto getBoardModifyDto(MultipartRequest multipartRequest) {
        patternValidator.validateModifyBoardRequest(multipartRequest);

        List<FileCreateDto> fileCreateList = this.getFileCreateDtoList(multipartRequest);
        List<Integer> deleteFileIdList = getFileDeleteIdList(multipartRequest);

        return new BoardModifyDto(
                Integer.parseInt(multipartRequest.getParameter(BOARD_ID_PARAM)),
                multipartRequest.getParameter(NAME_PARAM),
                multipartRequest.getParameter(PASSWORD_PARAM),
                multipartRequest.getParameter(TITLE_PARAM),
                multipartRequest.getParameter(CONTENT_PARAM),
                fileCreateList,
                deleteFileIdList
        );
    }

    public BoardModifyDto getBoardModifyDto(HttpServletRequest request) {
        MultipartRequest multipartRequest = multipartHandler.getMultipartRequest(request);

        patternValidator.validateModifyBoardRequest(multipartRequest);

        List<FileCreateDto> fileCreateList = this.getFileCreateDtoList(multipartRequest);
        List<Integer> deleteFileIdList = getFileDeleteIdList(multipartRequest);

        return new BoardModifyDto(
                Integer.parseInt(multipartRequest.getParameter(BOARD_ID_PARAM)),
                multipartRequest.getParameter(NAME_PARAM),
                multipartRequest.getParameter(PASSWORD_PARAM),
                multipartRequest.getParameter(TITLE_PARAM),
                multipartRequest.getParameter(CONTENT_PARAM),
                fileCreateList,
                deleteFileIdList
        );
    }


    public CommentCreateDto getCommentCreateDto(HttpServletRequest request) {
        patternValidator.validateCreateComment(request);

        return new CommentCreateDto(
                Integer.parseInt(request.getParameter(BOARD_ID_PARAM)),
                request.getParameter(CONTENT_PARAM)
        );
    }

    public int getBoardId(HttpServletRequest request) {
        return getIntParameterOrElseThrow(request, BOARD_ID_PARAM);
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
                .orElseThrow(() -> new IllegalArgumentException("invalid parameter " + parameter));
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

    private String getExtension(File file) {
        String[] nameSplitList = file.getName().split("[.]");
        String extension = nameSplitList[nameSplitList.length - 1];
        return extension;
    }


}

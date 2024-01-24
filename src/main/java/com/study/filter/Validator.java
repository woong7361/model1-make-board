package com.study.filter;

import com.oreilly.servlet.MultipartRequest;

import javax.servlet.http.HttpServletRequest;

import static com.study.constant.RequestParamConstant.*;
import static com.study.filter.PatternValidator.*;

/**
 * request param을 validate 하는 class
 */
public class Validator {
    private final PatternValidator patternValidator = new PatternValidator();

    /**
     * board 생성 요청으로 들어온 param을 검증한다.
     * @param multipartRequest
     */
    public void validateCreateBoardRequest(MultipartRequest multipartRequest) {
        patternValidator.validateBoardCategory(multipartRequest.getParameter(CATEGORY_PARAM));
        patternValidator.validateParameterPattern(multipartRequest.getParameter(PASSWORD_PARAM), PASSWORD_PATTERN);
        patternValidator.validateParameterPattern(multipartRequest.getParameter(NAME_PARAM), NAME_PATTERN);
        patternValidator.validateParameterPattern(multipartRequest.getParameter(TITLE_PARAM), TITLE_PATTERN);
        patternValidator.validateParameterPattern(multipartRequest.getParameter(BOARD_CONTENT_PARAM), CONTENT_PATTERN);

    }

    /**
     * comment 생성 요청으로 들어온 param을 검증한다.
     * @param request
     */
    public void validateCreateComment(HttpServletRequest request) {
        patternValidator.validateParameterPattern(request.getParameter(BOARD_ID_PARAM), PK_PATTERN);
        patternValidator.validateParameterPattern(request.getParameter(COMMENT_CONTENT_PARAM), CONTENT_PATTERN);

    }

    /**
     * board 수정 요청으로 들어온 param을 검증한다.
     * @param multipartRequest
     */
    public void validateModifyBoardRequest(MultipartRequest multipartRequest) {
        patternValidator.validateParameterPattern(multipartRequest.getParameter(NAME_PARAM), NAME_PATTERN);
        patternValidator.validateParameterPattern(multipartRequest.getParameter(PASSWORD_PARAM), PASSWORD_PATTERN);
        patternValidator.validateParameterPattern(multipartRequest.getParameter(TITLE_PARAM), TITLE_PATTERN);
        patternValidator.validateParameterPattern(multipartRequest.getParameter(BOARD_CONTENT_PARAM), CONTENT_PATTERN);
    }



}

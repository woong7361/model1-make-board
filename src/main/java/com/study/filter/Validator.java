package com.study.filter;

import com.oreilly.servlet.MultipartRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * request param을 validate 하는 class
 */
public class Validator {
    private final PatternValidator patternValidator = new PatternValidator();

    /**
     * board 생성 요청으로 들어온 param을 검증한다.
     * @param multipartRequest http multipart request
     */
    public void validateCreateBoardRequest(MultipartRequest multipartRequest) {
        patternValidator.validateCreateBoardRequest(multipartRequest);

    }

    /**
     * comment 생성 요청으로 들어온 param을 검증한다.
     * @param request http request
     */
    public void validateCreateComment(HttpServletRequest request) {
        patternValidator.validateCreateComment(request);
    }

    /**
     * board 수정 요청으로 들어온 param을 검증한다.
     * @param multipartRequest http multipart request
     */
    public void validateModifyBoardRequest(MultipartRequest multipartRequest) {
        patternValidator.validateModifyBoardRequest(multipartRequest);
    }
}

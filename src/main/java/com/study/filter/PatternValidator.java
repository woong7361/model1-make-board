package com.study.filter;

import com.oreilly.servlet.MultipartRequest;
import com.study.board.Category;
import com.study.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.study.constant.ExceptionConstant.INVALID_REQUEST_PARAM_MESSAGE;
import static com.study.constant.RequestParamConstant.*;

/**
 * 정규표현식을 이용해 pattern을 검증한다.
 */
public class PatternValidator {
    static final String NAME_PATTERN = "^[가-힣]{3,4}$";
    static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{4,15}$";
    static final String TITLE_PATTERN = ".{4,100}$";
    static final String CONTENT_PATTERN = ".{4,2000}$";
    static final String PK_PATTERN = "^[0-9]+$";

    private Logger logger = LoggerFactory.getLogger(PatternValidator.class);

    /**
     * board 생성 요청으로 들어온 param을 검증한다.
     * @param multipartRequest http multipart request
     */
    public void validateCreateBoardRequest(MultipartRequest multipartRequest) {
        validateBoardCategory(multipartRequest.getParameter(CATEGORY_PARAM));
        validateParameterPattern(multipartRequest.getParameter(PASSWORD_PARAM), PASSWORD_PATTERN);
        validateParameterPattern(multipartRequest.getParameter(NAME_PARAM), NAME_PATTERN);
        validateParameterPattern(multipartRequest.getParameter(TITLE_PARAM), TITLE_PATTERN);
        validateParameterPattern(multipartRequest.getParameter(BOARD_CONTENT_PARAM), CONTENT_PATTERN);

    }

    /**
     * comment 생성 요청으로 들어온 param을 검증한다.
     * @param request http request
     */
    public void validateCreateComment(HttpServletRequest request) {
        validateParameterPattern(request.getParameter(BOARD_ID_PARAM), PK_PATTERN);
        validateParameterPattern(request.getParameter(COMMENT_CONTENT_PARAM), CONTENT_PATTERN);

    }

    /**
     * board 수정 요청으로 들어온 param을 검증한다.
     * @param multipartRequest http multipart request
     */
    public void validateModifyBoardRequest(MultipartRequest multipartRequest) {
        validateParameterPattern(multipartRequest.getParameter(NAME_PARAM), NAME_PATTERN);
        validateParameterPattern(multipartRequest.getParameter(PASSWORD_PARAM), PASSWORD_PATTERN);
        validateParameterPattern(multipartRequest.getParameter(TITLE_PARAM), TITLE_PATTERN);
        validateParameterPattern(multipartRequest.getParameter(BOARD_CONTENT_PARAM), CONTENT_PATTERN);
    }

//    ------------------------------------------------------------------------------------------------------------------
    private void validateParameterPattern(String str, String pattern) {
        Optional
                .ofNullable(str)
                .filter((s) -> Pattern.matches(pattern, s))
                .orElseThrow(() -> {
                    logger.debug("pattern validate fail - pattern: {}, targetString: {}", pattern, str);
                    return new CustomException(INVALID_REQUEST_PARAM_MESSAGE);
                });
    }

    private void validateBoardCategory(String category) {
        Category[] categories = Category.values();

        boolean match = Arrays.stream(categories)
                .map(c -> c.name())
                .anyMatch(c -> c.equals(category));

        if (!match) {
            logger.debug("category validate fail - categories: {}, targetString: {}", categories, category);
            throw new CustomException(INVALID_REQUEST_PARAM_MESSAGE);
        }
    }

}

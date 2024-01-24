package com.study.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.study.constant.RequestParamConstant.*;

/**
 * URL에서 사용되는 Util class
 */
public class UrlUtil {

    /**
     * URI로 부터 게시글 검색에 관련된 queryString과 게시글 탐색에 관련된 boardId를 가져온다.
     *
     * @param request
     * @return 게시글을 검색하기 위한 queryString을 반환한다.
     */
    public static String getSearchParamWithBoardIdAndPage(HttpServletRequest request) {
        String queryString = getSearchParam(request);
        queryString = getNextQueryStringByParam(request, PAGE_PARAM, queryString);
        queryString = getNextQueryStringByParam(request, BOARD_ID_PARAM, queryString);

        return queryString;
    }

    /**
     * URI로 부터 게시글 검색에 관련된 queryString을 가져온다.
     *
     * @param request
     * @return 게시글을 검색하기 위한 queryString을 반환한다.
     */
    public static String getSearchParam(HttpServletRequest request) {
        String queryString = getFirstQueryStringByParam(request, KEY_PARAM);
        queryString = getNextQueryStringByParam(request, CATEGORY_PARAM, queryString);
        queryString = getNextQueryStringByParam(request, START_DATE_PARAM, queryString);
        queryString = getNextQueryStringByParam(request, END_DATE_PARAM, queryString);

        return queryString;
    }

//    ------------------------------------------------------------------------------------------------------------------

    private static String getFirstQueryStringByParam(HttpServletRequest request, String param) {
        return Optional.ofNullable(request.getParameter(param))
                .map((p) -> "?" + param + "=" + p)
                .orElse("?");
    }
    private static String getNextQueryStringByParam(HttpServletRequest request, String param, String prevQureyString) {
        return Optional.ofNullable(request.getParameter(param))
                .map((p) ->prevQureyString + "&" + param + "=" + p)
                .orElse(prevQureyString);
    }

}

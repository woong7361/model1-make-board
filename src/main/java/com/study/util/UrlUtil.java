package com.study.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlUtil {
    private static final String KEY_PARAM = "search_key";
    private static final String END_DATE_PARAM = "end_date";
    private static final String START_DATE_PARAM = "start_date";
    private static final String CATEGORY_PARAM = "search_category";
    private static final String PAGE_PARAM = "page";
    public static final String BOARD_ID_PARAM = "board_id";


    //param list로 받아서 chain으로 한번에 qureyString 생성하도록 리팩토링 필요
    public static String getSearchParamWithBoardIdAndPage(HttpServletRequest request) {
        String queryString = getFirstQueryStringByParam(request, KEY_PARAM);
        queryString = getNextQueryStringByParam(request, CATEGORY_PARAM, queryString);
        queryString = getNextQueryStringByParam(request, START_DATE_PARAM, queryString);
        queryString = getNextQueryStringByParam(request, END_DATE_PARAM, queryString);
        queryString = getNextQueryStringByParam(request, PAGE_PARAM, queryString);
        queryString = getNextQueryStringByParam(request, BOARD_ID_PARAM, queryString);

        return queryString;
    }

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

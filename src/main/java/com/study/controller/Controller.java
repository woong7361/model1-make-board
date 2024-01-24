package com.study.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    /**
     * 해당 컨트롤러에 맞는 서비스를 실행한다.
     * @param request http request
     * @param response http response
     */
    void service(HttpServletRequest request, HttpServletResponse response);
}

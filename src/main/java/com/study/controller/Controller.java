package com.study.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    void service(HttpServletRequest request, HttpServletResponse response);
}

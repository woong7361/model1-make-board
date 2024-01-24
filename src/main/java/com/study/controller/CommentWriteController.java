package com.study.controller;

import com.study.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 코멘트 생성 컨트롤러
 */
public class CommentWriteController implements Controller{
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        CommentService commentService = new CommentService();
        commentService.createComment(request, response);
    }
}

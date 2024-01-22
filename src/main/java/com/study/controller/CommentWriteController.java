package com.study.controller;

import com.study.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommentWriteController implements Controller{

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        CommentService commentService = new CommentService();
        commentService.createComment(request, response);
    }
}

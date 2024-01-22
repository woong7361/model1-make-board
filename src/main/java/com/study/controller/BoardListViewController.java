package com.study.controller;

import com.study.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardListViewController implements Controller{
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        BoardService boardService = new BoardService();
        boardService.listView(request, response);
    }
}

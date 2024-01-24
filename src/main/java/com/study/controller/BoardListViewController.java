package com.study.controller;

import com.study.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 게시글 리스트 화면 컨트롤러
 */
public class BoardListViewController implements Controller{
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        BoardService boardService = new BoardService();
        boardService.listView(request, response);
    }
}

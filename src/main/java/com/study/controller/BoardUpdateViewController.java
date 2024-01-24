package com.study.controller;

import com.study.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 게시글 업데이트 화면 컨트롤러
 */
public class BoardUpdateViewController implements Controller{
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        BoardService boardService = new BoardService();
        boardService.updateBoardView(request, response);

    }
}

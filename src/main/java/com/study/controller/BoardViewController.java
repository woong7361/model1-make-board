package com.study.controller;

import com.study.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * board view 요청을 처리
 */
public class BoardViewController implements Controller{
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)  throws IOException {
        BoardService boardService = new BoardService();
        boardService.view(request, response);
    }
}
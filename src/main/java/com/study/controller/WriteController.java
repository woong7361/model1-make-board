package com.study.controller;

import com.study.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * board를 작성하는 controller
 */
public class WriteController implements Controller {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws Exception{
        BoardService boardService = new BoardService();
        boardService.write(request, response);
    }
}

<%@ page import="com.study.board.dao.JdbcBoardDao" %>
<%@ page import="com.study.board.dao.BoardDao" %>
<%@ page import="com.study.board.dto.BoardCreateDto" %>
<%@ page import="com.study.board.Category" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 20.
  Time: 오전 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    BoardDao boardDao = new JdbcBoardDao();

    for (int i = 0; i < 5; i++) {
        BoardCreateDto boardCreateDto1 = new BoardCreateDto(
                Category.JAVA,
                "김이박",
                "abc123*" + i,
                "title" + i,
                "content" + i,
                new ArrayList<>()
        );
        boardDao.saveBoard(boardCreateDto1);

        BoardCreateDto boardCreateDto2 = new BoardCreateDto(
                Category.DATABASE,
                "김이박",
                "abc123*" + i,
                "title" + i,
                "content" + i,
                new ArrayList<>()
        );
        boardDao.saveBoard(boardCreateDto2);

        BoardCreateDto boardCreateDto3 = new BoardCreateDto(
                Category.JAVASCRIPT,
                "김이박",
                "abc123*" + i,
                "title" + i,
                "content" + i,
                new ArrayList<>()
        );
        boardDao.saveBoard(boardCreateDto3);
    }
%>


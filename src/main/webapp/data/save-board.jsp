<%@ page import="java.sql.*" %>
<%@ page import="com.study.board.*" %>
<%@ page import="com.study.board.dto.BoardCreateDto" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.study.filter.multitpart.MultipartHandler" %>
<%@ page import="com.study.file.dao.FileDao" %>
<%@ page import="com.study.file.dao.JdbcFileDao" %>
<%@ page import="com.study.filter.PatternValidator" %>
<%@ page import="com.study.filter.RequestHandler" %>
<%@ page import="com.study.filter.PatternValidator" %>
<%@ page import="com.study.board.dao.JdbcBoardDao" %>
<%@ page import="com.study.board.dao.BoardDao" %>
<%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 14.
  Time: 오전 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");

    MultipartHandler multipartHandler = new MultipartHandler();
    MultipartRequest multipartRequest = multipartHandler.getMultipartRequest(request);

    RequestHandler requestHandler = new RequestHandler();
    BoardCreateDto boardCreateDto = requestHandler.getBoardCreateDto(multipartRequest);


    BoardDao BoardDao = new JdbcBoardDao();
    int boardId = BoardDao.saveBoard(boardCreateDto);

    FileDao fileDao = new JdbcFileDao();
    fileDao.saveFileList(boardCreateDto.getFileList(), boardId);


    response.sendRedirect("/board/free/list.jsp");
%>

</body>
</html>

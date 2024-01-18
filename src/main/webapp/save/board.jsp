<%@ page import="java.sql.*" %>
<%@ page import="com.study.board.*" %>
<%@ page import="com.study.board.dto.BoardCreateDto" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.study.multipart.MultipartHandler" %>
<%@ page import="com.study.file.FileDao" %>
<%@ page import="com.study.file.JdbcFileDao" %>
<%@ page import="com.study.validate.RequestValidator" %>
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

    try {
        RequestValidator requestValidator = new RequestValidator();
        requestValidator.validateCreateBoardRequest(multipartRequest);
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
        response.sendRedirect("/error/error.jsp");
        return;
    }

    BoardCreateDto boardCreateDto = multipartHandler.getBoardCreateDto(multipartRequest);

    try {
        BoardDao BoardDao = new JdbcBoardDao();
        int boardId = BoardDao.saveBoard(boardCreateDto);

        FileDao fileDao = new JdbcFileDao();
        fileDao.saveFileList(boardCreateDto.getFileList(), boardId);
    } catch (SQLException e) {
        e.printStackTrace();
        response.sendRedirect("/error/error.jsp");
        return;
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("/error/error.jsp");
        return;
    }

    response.sendRedirect("/view/list.jsp");
%>

</body>
</html>

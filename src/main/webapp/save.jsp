<%@ page import="java.sql.*" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.study.board.*" %>
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

    String category = request.getParameter("category");
    String name = request.getParameter("name");
    String password = request.getParameter("password");
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    String file = request.getParameter("file");

    BoardCreateDto boardCreateDto = new BoardCreateDto(category, name, password, title, content, file);

    // ! operator 리팩토링 필요
    if (!ValidateBoard.validateCreateBoard(boardCreateDto)){
        response.sendRedirect("error.jsp");
        return;
    }

    try {
        BoardDao BoardDao = new JdbcBoardDao();
        BoardDao.saveBoard(boardCreateDto);
    } catch (SQLException e) {
        e.printStackTrace();
        response.sendRedirect("error.jsp");
        return;
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("error.jsp");
        return;
    }

    response.sendRedirect("index.jsp");
%>

</body>
</html>

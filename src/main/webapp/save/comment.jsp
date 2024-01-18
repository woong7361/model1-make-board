<%@ page import="com.study.board.JdbcBoardDao" %>
<%@ page import="com.study.board.BoardDao" %>
<%@ page import="com.study.comment.JdbcCommentDao" %>
<%@ page import="com.study.comment.CommentDao" %>
<%@ page import="com.study.validate.RequestValidator" %>
<%@ page import="com.study.comment.dto.CommentCreateDto" %><%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 16.
  Time: 오후 3:20
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

    try {
        RequestValidator requestValidator = new RequestValidator();
        requestValidator.validateCreateComment(request);
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
        response.sendRedirect("/error/error.jsp");
        return;
    }

    CommentCreateDto commentCreateDto = new CommentCreateDto(request);

    //ned validate content

    CommentDao commentDao = new JdbcCommentDao();
    commentDao.saveComment(commentCreateDto);

    response.sendRedirect("/view/board.jsp?board_id=" + commentCreateDto.getBoard_id());
%>

</body>
</html>

<%@ page import="com.study.comment.dao.JdbcCommentDao" %>
<%@ page import="com.study.comment.dao.CommentDao" %>
<%@ page import="com.study.comment.dto.CommentCreateDto" %>
<%@ page import="com.study.util.UrlUtil" %>
<%@ page import="com.study.filter.RequestHandler" %><%--
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

    RequestHandler requestHandler = new RequestHandler();
    CommentCreateDto commentCreateDto = requestHandler.getCommentCreateDto(request);


    CommentDao commentDao = new JdbcCommentDao();
    commentDao.saveComment(commentCreateDto);

    String searchParamWithBoardId = UrlUtil.getSearchParamWithBoardIdAndPage(request);
    response.sendRedirect("/board/free/view.jsp" + searchParamWithBoardId);
%>

</body>
</html>

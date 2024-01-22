<%@ page import="java.util.Optional" %>
<%@ page import="com.study.file.dao.FileDao" %>
<%@ page import="com.study.file.dao.JdbcFileDao" %>
<%@ page import="com.study.board.dao.JdbcBoardDao" %>
<%@ page import="com.study.board.dao.BoardDao" %>
<%@ page import="com.study.comment.dao.JdbcCommentDao" %>
<%@ page import="com.study.comment.dao.CommentDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="com.study.util.UrlUtil" %>
<%@ page import="com.study.filter.RequestHandler" %>
<%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 18.
  Time: 오후 4:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    RequestHandler requestHandler = new RequestHandler();
    int boardId = requestHandler.getBoardId(request);


    CommentDao commentDao = new JdbcCommentDao();
    commentDao.deleteByBoardId(boardId);

    FileDao fileDao = new JdbcFileDao();
    List<String> filePathList = fileDao.getFilePathListByBoardId(boardId);
    fileDao.deleteByBoardId(boardId);
    for (String path : filePathList) {
        File file = new File(path);
        file.delete();
    }

    BoardDao boardDao = new JdbcBoardDao();
    boardDao.deleteByBoardId(boardId);

    String searchParam = UrlUtil.getSearchParam(request);
    response.sendRedirect("/board/free/list.jsp"+searchParam);
    //TODO: 메모
%>
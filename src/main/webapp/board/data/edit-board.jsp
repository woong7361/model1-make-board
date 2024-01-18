<%@ page import="com.study.multipart.MultipartHandler" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.study.validate.RequestValidator" %>
<%@ page import="com.study.board.dto.BoardModifyDto" %>
<%@ page import="com.study.board.JdbcBoardDao" %>
<%@ page import="com.study.board.BoardDao" %>
<%@ page import="com.study.file.JdbcFileDao" %>
<%@ page import="com.study.file.FileDao" %><%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 18.
  Time: 오후 4:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    request.setCharacterEncoding("UTF-8");

    MultipartHandler multipartHandler = new MultipartHandler();
    MultipartRequest multipartRequest = multipartHandler.getMultipartRequest(request);

    try {
        RequestValidator requestValidator = new RequestValidator();
        requestValidator.validateModifyBoardRequest(multipartRequest);
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
        response.sendRedirect("/error/error.jsp");
        return;
    }

    BoardModifyDto boardModifyDto = multipartHandler.getBoardModifyDto(multipartRequest);

    BoardDao boardDao = new JdbcBoardDao();
    boardDao.updateBoard(boardModifyDto);

    FileDao fileDao = new JdbcFileDao();
    fileDao.deleteFileList(boardModifyDto.getDeleteFileIdList());
    fileDao.saveFileList(boardModifyDto.getCreateFileList(), boardModifyDto.getBoard_id());

    response.sendRedirect("/board/free/view.jsp?board_id=" + boardModifyDto.getBoard_id());
%>

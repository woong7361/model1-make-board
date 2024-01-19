<%@ page import="com.study.filter.multitpart.MultipartHandler" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.study.board.dto.BoardModifyDto" %>
<%@ page import="com.study.board.dao.JdbcBoardDao" %>
<%@ page import="com.study.board.dao.BoardDao" %>
<%@ page import="com.study.file.dao.JdbcFileDao" %>
<%@ page import="com.study.file.dao.FileDao" %>
<%@ page import="com.study.util.UrlUtil" %>
<%@ page import="com.study.filter.RequestHandler" %>
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

    RequestHandler requestHandler = new RequestHandler();
    BoardModifyDto boardModifyDto = requestHandler.getBoardModifyDto(multipartRequest);

    BoardDao boardDao = new JdbcBoardDao();
    boardDao.updateBoard(boardModifyDto);

    FileDao fileDao = new JdbcFileDao();
    fileDao.deleteFileList(boardModifyDto.getDeleteFileIdList());
    fileDao.saveFileList(boardModifyDto.getCreateFileList(), boardModifyDto.getBoard_id());

    String searchParamWithBoardId = UrlUtil.getSearchParamWithBoardIdAndPage(request);


    response.sendRedirect("/board/free/view.jsp" + searchParamWithBoardId);
%>
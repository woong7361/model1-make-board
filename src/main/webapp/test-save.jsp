<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.FileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="java.io.File" %>
<%@ page import="com.study.FileHandler" %>
<%@ page import="com.study.board.dto.BoardCreateDto" %>
<%@ page import="com.study.multipart.MultipartHandler" %><%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 17.
  Time: 오전 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String saveDirectory = application.getRealPath("/files");
    int maxPostSize = 1024 * 1024 * 5; // 5MB  단위 byte
    String encoding = "UTF-8";
    FileRenamePolicy policy = new DefaultFileRenamePolicy();

    new MultipartHandler().getMultipartRequest(request);

//    MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
//    request.setCharacterEncoding("UTF-8");
//
//    String category = multipartRequest.getParameter("category");
//    String name = multipartRequest.getParameter("name");
//    String password = multipartRequest.getParameter("password");
//    String title = multipartRequest.getParameter("title");
//    String content = multipartRequest.getParameter("content");
//    File file = multipartRequest.getFile("file");
//    String original = multipartRequest.getOriginalFileName("file");
//    String fileSystemName = multipartRequest.getFilesystemName("file");
//    System.out.println("original = " + original);
//    System.out.println("fileSystemName = " + fileSystemName);
//    System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
//    System.out.println("file.getPath() = " + file.getPath());



//    BoardCreateDto boardCreateDto = new BoardCreateDto(category, name, password, title, content, file);

%>
</body>
</html>

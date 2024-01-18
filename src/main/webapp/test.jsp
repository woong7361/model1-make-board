
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.io.FileInputStream" %>
<%@ page import="com.study.board.Category" %>
<%@ page import="com.study.multipart.MultipartHandler" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%
    String[] names = request.getParameterValues("name");
    MultipartHandler multipartHandler = new MultipartHandler();
    MultipartRequest multipartRequest = multipartHandler.getMultipartRequest(request);

    System.out.println("names = " + names.toString());
%>
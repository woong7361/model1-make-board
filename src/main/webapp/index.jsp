<%@ page import="com.study.connection.ConnectionTest" %>
<%@ page import="com.study.connection.ConnectionPool" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>


<%
    System.out.println("hello = " + "hello");

%>

</body>
</html>

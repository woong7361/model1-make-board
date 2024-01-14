<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="com.study.connection.ConnectionPool" %><%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 14.
  Time: 오후 1:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%

    Connection connection = ConnectionPool.getConnection();
    String sql = "CREATE TABLE board (" +
            "board_id INT AUTO_INCREMENT PRIMARY KEY, " +
            "category VARCHAR(10) NOT NULL, " +
            "name VARCHAR(10) NOT NULL, " +
            "password VARCHAR(200) NOT NULL, " +
            "title VARCHAR(20) NOT NULL, " +
            "content VARCHAR(2000) NOT NULL, " +
            "view INT NOT NULL, " +
            "created_at TIMESTAMP , " +
            "modified_at TIMESTAMP " +
            ")";

    Statement statement = connection.createStatement();

    statement.execute(sql);
    statement.close();
    connection.close();

%>
</body>
</html>

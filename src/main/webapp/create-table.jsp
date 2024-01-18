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


    String createBoardTableSql = "CREATE TABLE board (" +
            "board_id INT AUTO_INCREMENT PRIMARY KEY, " +
            "category VARCHAR(10) NOT NULL, " +
            "name VARCHAR(10) NOT NULL, " +
            "password VARCHAR(200) NOT NULL, " +
            "title VARCHAR(20) NOT NULL, " +
            "content TEXT NOT NULL, " +
            "view INT NOT NULL, " +
            "created_at TIMESTAMP , " +
            "modified_at TIMESTAMP " +
            ")";
    Statement boardStatement = connection.createStatement();
    boardStatement.execute(createBoardTableSql);
    boardStatement.close();


    String createCommentTableSql = "CREATE TABLE comment (" +
            "comment_id INT AUTO_INCREMENT PRIMARY KEY, " +
            "board_id INT NOT NULL, " +
            "content TEXT NOT NULL, " +
            "created_at TIMESTAMP, " +
            "FOREIGN KEY (board_id) REFERENCES board (board_id)" +
            ")";
    Statement commentStatement = connection.createStatement();
    commentStatement.execute(createCommentTableSql);
    commentStatement.close();


    String createFileTableSql = "CREATE TABLE file (" +
            "file_id INT AUTO_INCREMENT PRIMARY KEY, " +
            "board_id INT NOT NULL, " +
            "original_name VARCHAR(100) NOT NULL, " +
            "name VARCHAR(200) NOT NULL, " +
            "path VARCHAR(200) NOT NULL, " +
            "extension VARCHAR(10) NOT NULL, " +
            "FOREIGN KEY (board_id) REFERENCES board (board_id)" +
            ")";
    Statement fileStatement = connection.createStatement();
    fileStatement.execute(createFileTableSql);
    fileStatement.close();

//    String createCategoryTableSql = "CREATE TABLE category (" +
//            "category_id INT AUTO_INCREMENT PRIMARY KEY, " +
//            "category VARCHAR(100) NOT NULL" +
//            ")";
//    Statement categoryStatement = connection.createStatement();
//    fileStatement.execute(createCategoryTableSql);
//    fileStatement.close();


    connection.close();
%>
</body>
</html>

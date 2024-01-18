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
    <script type="text/javascript">
        function send(){
            let send_form = document.create_form

            send_form.action = "test.jsp";
            send_form.submit()
        }
    </script>
</head>
<body>
<form action="" method="post" name="create_form">
    <input type="text" value="1 " name="name"/>
    <input type="text" value="2 " name="name"/>

    <input type="button" value="저장" onclick="send();"/>
</form>

</body>
</html>

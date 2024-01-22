<%@ page import="com.study.util.UrlUtil" %><%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 13.
  Time: 오후 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String searchParam = UrlUtil.getSearchParam(request);
%>
<link href="/css/write.css" rel="stylesheet">
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
        function send(){
            const category_pattern = /^(JAVA|JAVASCRIPT|DATABASE)$/;
            const name_pattern = /^[가-힣]{3,4}$/;
            const title_pattern = /.{4,100}$/;
            const content_pattern = /.{4,2000}$/;
            const password_pattern = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{4,15}$/;


            let send_form = document.create_form
            verify_pattern(send_form.category, category_pattern);
            verify_pattern(send_form.name, name_pattern);
            verify_pattern(send_form.title, title_pattern);
            verify_pattern(send_form.content, content_pattern);
            verify_password(send_form.password, send_form.password_confirm, password_pattern);

            // send_form.action = "/data/save-board.jsp";
            send_form.action = "/controller/board/create";
            send_form.submit()
        }

        function verify_pattern(tag, pattern) {
            if (!pattern.test(tag.value)) {
                alert('invalid ' + tag.name);
                throw "invalid " + tag.name;
            }
        }

        function verify_password(password, password_confirm, pattern) {
            verify_pattern(password, pattern);
            verify_pattern(password_confirm, pattern);

            if (password.value != password_confirm.value) {
                alert('password was not coincide with password_confirm')
                throw "invalid password"
            }
        }
    </script>
</head>
<body>
<div>
    <h1>게시판 등록</h1>

    <form action="" method="post" name="create_form" enctype="multipart/form-data">
        <div id="create_form">
            <table>
            <tbody>
                <tr>
                    <th>카테고리</th>
                    <td>
                        <select name="category">
                            <option value="JAVA">JAVA</option>
                            <option value="DATABASE">DATABASE</option>
                            <option value="JAVASCRIPT">JAVASCRIPT</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td><input type="text" name="name"/></td>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td><input type="text" name="password" placeholder="비밀번호"/><input type="text" name="password_confirm" placeholder="비밀번호 확인"/></td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td><input type="text" name="title"/></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><textarea name="content" cols="80" rows="10"></textarea></td>
                </tr>
                <tr>
                    <th>파일</th>
                    <td><input type="file" name="file_add1"/> <br/><input type="file" name="file_add2"/> <br/> <input type="file" name="file_add3"/></td>
                </tr>
            </tbody>
            </table>
        </div>

        <div id="buttons">
            <input type="button" value="취소" onclick="location.href='/board/free/list.jsp<%=searchParam%>';"/>
            <input type="button" value="등록" onclick="send();"/>
        </div>
    </form>
</div>
</body>
</html>

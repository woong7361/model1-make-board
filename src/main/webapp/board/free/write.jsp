<%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 13.
  Time: 오후 10:38
  To change this template use File | Settings | File Templates.
--%>
<%String cp = request.getContextPath();%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="/css/write.css" rel="stylesheet">
<html>
<head>
    <title>Title</title>


    <script type="text/javascript">
        function send(){
            let send_form = document.create_form

            verify_category(send_form.category.value);
            verify_name(send_form.name.value);
            verify_password(send_form.password.value, send_form.password_confirm.value);
            verify_title(send_form.title.value);
            verify_content(send_form.content.value)

            send_form.action = "<%=cp%>/save/board.jsp";
            send_form.submit()
        }

        function verify_category(category) {
            if (category == null) {
                alert('select category please')
                // send_form.title.focus()
                throw "invalid category"
            }
        }

        function verify_name(name) {
            const name_test_regex = /^[가-힣]{3,4}$/
            if (!name_test_regex.test(name)) {
                alert('correct name please')
                throw "invalid name"
            }
        }

        function verify_password(password, password_confirm) {
            if (password != password_confirm) {
                alert('password was not coincide with password_confirm')
                throw "invalid password"
            }

            const password_test_regex = /^[a-zA-Z0-9#?!@$%^&*-]{4,15}$/
            if (!password_test_regex.test(password)){
                alert('invalid password')
                throw "invalid password"
            }
        }

        function verify_title(title) {
            const title_test_regex = /.{4,100}$/
            if (!title_test_regex.test(title)) {
                alert('correct title please')
                throw "invalid title"
            }
        }

        function verify_content(content) {
            const content_test_regex = /.{4,2000}$/
            if (!content_test_regex.test(content)) {
                alert('correct content please')
                throw "invalid content"
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
            <input type="button" value="취소" onclick="location.href='/board/free/list.jsp';"/>
            <input type="button" value="등록" onclick="send();"/>
        </div>
    </form>
</div>
</body>
</html>

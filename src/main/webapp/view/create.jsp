<%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 13.
  Time: 오후 10:38
  To change this template use File | Settings | File Templates.
--%>
<%String cp = request.getContextPath();%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">

        function verify_file(file) {
            //verify
        }

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

<div id="create_board">

    <div id="main_title">
        게시판 등록
    </div>

    <form action="" method="post" name="create_form" enctype="multipart/form-data">
    <div id="create_form">
        <div id="category">
            <dl>
                <dt>카테고리*</dt>
                <dd>
                    <select name="category">
                        <option value="java">java</option>
                        <option value="database">database</option>
                        <option value="javascript">javascript</option>
                    </select>
                </dd>
            </dl>
        </div>

        <div id="name">
            <dl>
                <dt>이름*</dt>
                <dd>
                    <input type="text" name="name"/>
                </dd>
            </dl>
        </div>

        <div id="password">
            <dl>
                <dt>비밀번호*</dt>
                <dd>
                    <input type="text" name="password"/>
                </dd>
                <dt>비밀번호 확인*</dt>
                <dd>
                    <input type="text" name="password_confirm"/>
                </dd>
            </dl>
        </div>

        <div id="title">
            <dl>
                <dt>제목*</dt>
                <dd>
                    <input type="text" name="title"/>
                </dd>
            </dl>
        </div>

        <div id="content">
            <dl>
                <dt>내용*</dt>
                <dd>
                    <textarea rows="10" cols="20" name="content"></textarea>
                </dd>
            </dl>

        </div>
        <div id="file">
            <dl>
                <dt>파일 첨부</dt>
                <dd>
                    <input type="file" name="file1"/>
                    <input type="file" name="file2"/>
                    <input type="file" name="file3"/>
                </dd>
            </dl>
        </div>
    </div>

    <div id="buttons">
        <input type="button" value="저장" onclick="send();"/>
<%--        <input type="button" value="취소" onclick="javascript:location.href='<%=cp%>/board/list.jsp';"/>--%>
    </div>
    </form>

</div>


</body>
</html>

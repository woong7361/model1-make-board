<%@ page import="com.study.board.dto.BoardDto" %>
<%@ page import="com.study.file.dto.FileDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.util.UrlUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 18.
  Time: 오후 1:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    request.setCharacterEncoding("UTF-8");

    int boardId = (int) request.getAttribute("boardId");
    BoardDto boardDto = (BoardDto) request.getAttribute("boardDto");
    List<FileDto> fileList = (List<FileDto>) request.getAttribute("fileList");

    String searchParamWithBoardId = UrlUtil.getSearchParamWithBoardIdAndPage(request);
%>
<head>
    <title>Title</title>
    <link href="/css/modify.css" rel="stylesheet">
    <script type="text/javascript">
        function send(){
            const name_pattern = /^[가-힣]{3,4}$/;
            const title_pattern = /.{4,100}$/;
            const content_pattern = /.{4,2000}$/;
            const password_pattern = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{4,15}$/;

            let send_form = document.modify_form

            verify_pattern(send_form.name, name_pattern);
            verify_pattern(send_form.title, title_pattern);
            verify_pattern(send_form.content, content_pattern);
            verify_pattern(send_form.password, password_pattern);

            send_form.action = "/controller/board/update<%=searchParamWithBoardId%>";
            send_form.submit()
        }

        function file_delete(str, file_id) {
            document.getElementById(str).style.display = 'none';
            document.getElementsByName(str)[0].type = 'text';
            document.getElementsByName(str)[0].value = file_id;
        }

        function verify_pattern(tag, pattern) {
            if (!pattern.test(tag.value)) {
                alert('invalid ' + tag.name);
                throw "invalid " + tag.name;
            }
        }

    </script>
</head>
<body>
<div id="modify_board">
    <h1 id="main_title">게시판 - 수정</h1>
    <form action="" method="post" name="modify_form" enctype="multipart/form-data">
        <div id="modify_form">
            <input type="text" name="board_id" value="<%=boardId%>" style="display: none">
            <table>
                <tr>
                    <th>카테고리</th>
                    <td><%=boardDto.getCategory().name()%></td>
                </tr>
                <tr>
                    <th>등록 일시</th>
                    <td><%=boardDto.getCreatedAtString()%></td>
                </tr>
                <tr>
                    <th>수정 일시</th>
                    <td><%=boardDto.getModifiedAt()%></td>
                </tr>
                <tr>
                    <th>조회수</th>
                    <td><%=boardDto.getView()%></td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td><input type="text" name="name" value="<%=boardDto.getName()%>"/></td>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td><input type="text" name="password"/></td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td><input type="text" name="title"/></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><textarea  cols="80" rows="10"  name="content"><%=boardDto.getContent()%></textarea></td>
                </tr>
                <tr>
                    <th>파일 첨부</th>
                    <td>
                    <%for(int i = 1; i < 4; i++){ %>
                        <%if(fileList.isEmpty()) {%>
                            <input type="file" name="file_add<%=i%>"/>
                        <%} else {%>
                            <div id="file_delete<%=i%>">
                                <%FileDto file = fileList.remove(0);%>
                                <%=file.getOriginalName()%>
                                <input type="button" value="Download" onclick="location.href='/file/download.jsp?file_id=<%=file.getFileId()%>'">
                                <input type="button" name="file_delete<%=i%>" value="X" onclick="file_delete('file_delete<%=i%>', <%=file.getFileId()%>)">
                            </div>
                        <%} %>
                    <%} %>
                    </td>
                </tr>
            </table>
            <div id="buttons">
                <input type="button" value="취소" onclick="location.href='/controller/board/view/list<%=searchParamWithBoardId%>'"/>
                <input type="button" value="저장" onclick="send();"/>
            </div>
        </div>
    </form>

</div>


</body>
</html>

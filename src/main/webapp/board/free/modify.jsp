<%@ page import="com.study.board.JdbcBoardDao" %>
<%@ page import="com.study.board.BoardDao" %>
<%@ page import="java.util.Optional" %>
<%@ page import="com.study.board.dto.BoardDto" %>
<%@ page import="com.study.file.JdbcFileDao" %>
<%@ page import="com.study.file.FileDao" %>
<%@ page import="com.study.file.FileDto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 18.
  Time: 오후 1:43
  To change this template use File | Settings | File Templates.
--%>
<%!
    public static final String KEY_PARAM = "search_key";
    public static final String END_DATE_PARAM = "end_date";
    public static final String START_DATE_PARAM = "start_date";
    public static final String CATEGORY_PARAM = "search_category";
    public static final String PAGE_PARAM = "page";
%>

<%String cp = request.getContextPath();%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    request.setCharacterEncoding("UTF-8");

    int boardId = Optional.ofNullable(request.getParameter("board_id"))
            .map((id) -> Integer.parseInt(id))
            .orElseThrow(() -> new IllegalArgumentException("invalid board_id"));

    BoardDao boardDao = new JdbcBoardDao();
    BoardDto boardDto = boardDao.getBoardByBoardId(boardId).
            orElseThrow(() -> new IllegalArgumentException("invalid board_id"));

    FileDao fileDao = new JdbcFileDao();
    List<FileDto> fileList = fileDao.getFileByBoardId(boardId);

    String searchKey = request.getParameter(KEY_PARAM);
    String searchCategory = request.getParameter(CATEGORY_PARAM);
    String searchStartDate = request.getParameter(START_DATE_PARAM);
    String searchEndDate = request.getParameter(END_DATE_PARAM);
    String currentPage = request.getParameter(PAGE_PARAM);

    String searchParam =
            "?" +
            "search_category=" + searchCategory +
            "&search_key=" + searchKey +
            "&start_date=" + searchStartDate +
            "&end_date=" + searchEndDate +
            "&page=" + currentPage +
            "&board_id=" + boardId;

%>
<head>
    <title>Title</title>
    <link href="/css/modify.css" rel="stylesheet">
    <script type="text/javascript">
        function send(){
            let send_form = document.modify_form
            send_form.action = "<%=cp%>/board/data/edit-board.jsp";
            send_form.submit()
        }

        function file_delete(str, file_id) {
            document.getElementById(str).style.display = 'none';
            document.getElementsByName(str)[0].type = 'text';
            document.getElementsByName(str)[0].value = file_id;
        }

        function verify_category(category) {
            if (category == null) {
                alert('select category please')
                // send_form.title.focus()
                throw "invalid category"
            }
        }

        function verify_pattern(str, pattern) {
            const name_test_regex = /^[가-힣]{3,4}$/
            if (!name_test_regex.test(name)) {
                alert('correct name please')
                throw "invalid name"
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
                    <td><%=boardDto.getCreatedAt().toString()%></td>
                </tr>
                <tr>
                    <th>수정 일시</th>
                    <td><%=boardDto.getModifiedAt().toString()%></td>
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
            <input type="button" value="취소" onclick="location.href='/board/free/view.jsp<%=searchParam%>'"/>
            <input type="button" value="저장" onclick="send();"/>
        </div>
    </form>

</div>


</body>
</html>

<%@ page import="com.study.board.BoardDao" %>
<%@ page import="com.study.board.JdbcBoardDao" %>
<%@ page import="com.study.board.dto.BoardDto" %>
<%@ page import="java.util.Optional" %>
<%@ page import="com.study.comment.dto.CommentDto" %>
<%@ page import="com.study.file.JdbcFileDao" %>
<%@ page import="com.study.file.FileDao" %>
<%@ page import="com.study.file.FileDto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 16.
  Time: 오전 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>board</title>
<%String cp = request.getContextPath();%>
<%

    int boardId = Integer.parseInt(request.getParameter("board_id"));
    BoardDao boardDao = new JdbcBoardDao();
    BoardDto boardDto = boardDao
            .getBoardByBoardId(boardId)
            .orElseThrow(() -> new IllegalArgumentException());

    FileDao fileDao = new JdbcFileDao();
    List<FileDto> fileDtoList = fileDao.getFileByBoardId(boardId);

    // increase board view

    //

%>

</head>
    <script type="text/javascript">
        function send(){
            let create_form = document.create_form
            verify_content(create_form.content.value)

            create_form.action = "<%=cp%>/save/comment.jsp";
            create_form.submit()

            function verify_content(content) {
                const comment_test_regex = /.{4,100}$/
                if (!comment_test_regex.test(content)) {
                    alert('correct comment please')
                    throw "invalid comment"
                }
            }
        }

    </script>
<body>
<div id="board">
    <div id="main_title">
        게시판 -보기
    </div>
    <div id="board_main">
        <div id="board_category">
            <%=boardDto.getCategory().toString()%>
        </div>
        <div id="board_title">
            <%=boardDto.getTitle().toString()%>
        </div>
        <div id="board_created_at">
            <%=boardDto.getCreatedAt().toString()%>
        </div>
        <div id="board_modified_at">
            <%=boardDto.getModifiedAt().toString()%>
        </div>
        <div id="board_view">
            <%=boardDto.getView()%>
        </div>
        <div id="board_content">
            <%=boardDto.getContent()%>
        </div>
    </div>

    <div id="board_file">
        <h3>파일 다운로드</h3>
        <%for(FileDto fileDto : fileDtoList){ %>
        <dl>
            <a href="/file/download.jsp?file_name=<%=fileDto.getName()%>&original_name=<%=fileDto.getOriginalName()%>">파일 - <%=fileDto.getOriginalName()%></a>
        </dl>
        <%} %>
    </div>

    <div id="board_comment">
        <h3>댓글 창</h3>
        <%for(CommentDto comment : boardDto.getCommentList()){ %>
        <dl>
            <dd class="created_at"><%=comment.getCreatedAt() %></dd>
            <dd class="content"><%=comment.getContent()%></dd>
        </dl>
        <%} %>
        <form action="" method="post" name="create_form">
            <textarea rows="10" cols="20" name="content"></textarea>
            <input type="hidden" name="board_id" value="<%=boardDto.getBoardId()%>">
            <input type="button" value="등록" onclick="send()">
        </form>
    </div>

    <div id="board_footer">
        <button>목록</button>
        <button>수정</button>
        <button>삭제</button>
    </div>
</div>
</body>
</html>

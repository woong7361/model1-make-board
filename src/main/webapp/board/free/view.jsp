<%@ page import="com.study.board.dao.BoardDao" %>
<%@ page import="com.study.board.dao.JdbcBoardDao" %>
<%@ page import="com.study.board.dto.BoardDto" %>
<%@ page import="com.study.comment.dto.CommentDto" %>
<%@ page import="com.study.file.dao.JdbcFileDao" %>
<%@ page import="com.study.file.dao.FileDao" %>
<%@ page import="com.study.file.dto.FileDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.comment.dao.JdbcCommentDao" %>
<%@ page import="com.study.comment.dao.CommentDao" %>
<%@ page import="java.util.Optional" %>
<%@ page import="com.study.util.UrlUtil" %>
<%--
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
<%

    int boardId = Optional.ofNullable(request.getParameter("board_id"))
            .map((id) -> Integer.parseInt(id))
            .orElseThrow(() -> new IllegalArgumentException("invalid board_id"));

    BoardDao boardDao = new JdbcBoardDao();

    BoardDto boardDto = boardDao
            .getBoardByBoardId(boardId)
            .orElseThrow(() -> new IllegalArgumentException());

    FileDao fileDao = new JdbcFileDao();
    List<FileDto> fileDtoList = fileDao.getFileByBoardId(boardId);

    CommentDao commentDao = new JdbcCommentDao();
    List<CommentDto> commentList = commentDao.getCommentByBoardId(boardId);

    boardDao.addBoardViewByBoardId(boardId);

    String searchParamWithBoardId = UrlUtil.getSearchParamWithBoardIdAndPage(request);
%>

</head>
    <link href="/css/view.css" rel="stylesheet">
    <script type="text/javascript">
        function send(){
            const content_pattern = /.{4,100}$/

            let create_form = document.create_form
            verify_pattern(create_form.content, content_pattern)
            create_form.action = "/data/save-comment.jsp<%=searchParamWithBoardId%>";
            create_form.submit()
        }

        function verify_pattern(tag, pattern) {
            if (!pattern.test(tag.value)) {
                alert('invalid ' + tag.name);
                throw "invalid " + tag.name;
            }
        }

    </script>
<body>
<div id="board">
    <div id="main_title">
        게시판 -보기
    </div>
    <div id="board_main">
        <div id="board_name"> <%=boardDto.getName()%> </div>
        <div id="board_time">
            등록일시 <%=boardDto.getCreatedAtString()%>
            &nbsp&nbsp&nbsp&nbsp
            수정일시 <%=boardDto.getModifiedAt()%>
        </div>
        <div id="board_title">
            [<%=boardDto.getCategory().toString()%>]
            &nbsp&nbsp&nbsp&nbsp
            <%=boardDto.getTitle()%>
        </div>
        <div id="board_view">
            조회수: <%=boardDto.getView()+1%>
        </div>
        <div id="board_content">
            <%=boardDto.getContent()%>
        </div>
    </div>

    <div id="board_file">
        <h3>파일 다운로드</h3>
        <%for(FileDto fileDto : fileDtoList){ %>
        <dl>
            <a href="/file/download.jsp?file_id=<%=fileDto.getFileId()%>">＊ 파일 - <%=fileDto.getOriginalName()%></a>
        </dl>
        <%} %>
    </div>

    <div id="board_comment">
        <h3>댓글 창</h3>
        <%for(CommentDto comment : commentList){ %>
        <dl id="comment">
            <dd class="created_at"><%=comment.getCreatedAtString() %></dd>
            <dd class="content"><%=comment.getContent()%></dd>
        </dl>
        <%} %>
        <form action="" method="post" name="create_form">
            <textarea rows="7" cols="100" name="content"></textarea>
            <input type="hidden" name="board_id" value="<%=boardDto.getBoardId()%>">
            <input type="button" value="등록" onclick="send()">
        </form>
    </div>

    <div id="board_footer">
        <button onclick="location.href='/board/free/list.jsp<%=searchParamWithBoardId%>'">목록</button>
        <button onclick="location.href='/board/free/modify.jsp<%=searchParamWithBoardId%>'">수정</button>
        <button onclick="location.href='/data/delete-board.jsp<%=searchParamWithBoardId%>'">삭제</button>
    </div>
</div>
</body>
</html>

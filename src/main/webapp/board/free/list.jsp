<%@ page import="com.study.board.dto.BoardListDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="static com.study.util.UrlUtil.getSearchParam" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    private static final int PAGE_OFFSET = 3;
%>
<%
    request.setCharacterEncoding("UTF-8");

    int currentPage = (int) request.getAttribute("currentPage");
    int totalCount = (int) request.getAttribute("totalCount");
    List<BoardListDto> boardList = (List<BoardListDto>) request.getAttribute("boardList");

    //TODO view logic으로 따로 빼기
    List<String> pageLinkList = new ArrayList<>();
    pageLinkList.add("<font color=\"Fuchsia\">" + currentPage + "</font>&nbsp;\n");

    int previousPage = currentPage - 1;
    int nextPage = currentPage + 1;
    String searchParam = getSearchParam(request);
    String searchUrl = "/controller/board/view/list" + searchParam;
    while (pageLinkList.size() < 5) {
        if (previousPage >= 0) {
            pageLinkList.add(0, "<a href=\"" + searchUrl + "&page=" + previousPage + "\">"+ previousPage +"</a>&nbsp;");
            previousPage--;
        }
        if (nextPage * PAGE_OFFSET < totalCount) {
            pageLinkList.add("<a href=\"" + searchUrl + "&page=" + nextPage + "\">" + nextPage + "</a>&nbsp;");
            nextPage++;
        }
        if (previousPage < 0 && nextPage * PAGE_OFFSET >= totalCount) break;
    }

%>
<html>
<head>
    <title>list view</title>
    <link href="/css/list.css" rel="stylesheet">
    <script type="text/javascript">
        function search(){
            let search_form = document.search_form

            search_form.action = "/controller/board/view/list";
            search_form.submit()
        }
    </script>
</head>
<body>
<div id="list">
    <h1>자유 게시판 - 목록</h1>
    <div id="list_search">
        <form action="" method="get" name="search_form">
            등록일
            <input type="date" name="start_date" value="2024-01-01" min="2023-01-01" max="2025-12-12">
            ~
            <input type="date" name="end_date" value="2025-01-01" min="2023-01-01" max="2025-12-12">
            <select name="search_category">
                <option value="ALL">전체 카테고리</option>
                <option value="JAVA">JAVA</option>
                <option value="JAVASCRIPT">JAVASCRIPT</option>
                <option value="DATABASE">DATABASE</option>
            </select>
            <input type="text" name="search_key" placeholder="검색어를 입력하세요"/>
            <input type="button" value=" 검 색 " onclick="search();"/>
        </form>
    </div>

    <div id="list_count">
        총 <%=totalCount%>건
    </div>

    <div id="main_list">
        <table>
            <thead>
            <tr>
                <th>카테고리</th>
                <th></th>
                <th>제목</th>
                <th>작성자</th>
                <th>조회수</th>
                <th>등록 일시</th>
                <th>수정 일시</th>
            </tr>
            </thead>
            <tbody>
                <%for(BoardListDto board : boardList){ %>
                <tr>
                    <td><%=board.getCategory().toString()%></td>
                    <%if (board.isHavaFile()) {%>
                        <td style="text-align: center"> ＊ </td>
                    <%} else {%>
                        <td></td>
                    <%} %>
                    <td>
                        <a href="/controller/board/view<%=searchParam%>&page=<%=currentPage%>&board_id=<%=board.getBoardId()%>">
                            <%=board.getTitle() %>
                        </a>
                    </td>
                    <td><%=board.getName() %></td>
                    <td><%=board.getView() %></td>
                    <td><%=board.getCreatedAtString() %></td>
                    <%if (board.getCreatedAtString().equals(board.getModifiedAtString())) {%>
                        <td style="text-align: center"> - </td>
                    <%} else {%>
                        <td><%=board.getModifiedAtString() %></td>
                    <%} %>
                </tr>
                <%} %>
            </tbody>
        </table>
            <div id="page_link"><%=pageLinkList%></div>
        </div>


        <div id="create_board_link">
            <button onclick="location.href='/controller/board/view/write<%=searchParam%>';">등록</button>
        </div>
    </div>
</div>
</body>
</html>

<%@ page import="com.study.board.JdbcBoardDao" %>
<%@ page import="com.study.board.BoardDao" %>
<%@ page import="java.util.Optional" %>
<%@ page import="com.study.board.dto.BoardListDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    private static final int PAGE_OFFSET = 2;
    public static final int FIRST_PAGE = 0;
    public static final String KEY_PARAM = "search_key";
    public static final String END_DATE_PARAM = "end_date";
    public static final String START_DATE_PARAM = "start_date";
    public static final String CATEGORY_PARAM = "search_category";

    public static final String PAGE_PARAM = "page";
    public static final String FIND_ALL = "all";

    public static final String DEFAULT_START_DATE = "2024-01-01";
    public static final String DEFAULT_END_DATE = "2025-01-01";
%>
<%
    request.setCharacterEncoding("UTF-8");
    String cp = request.getContextPath();

    BoardDao boardDao = new JdbcBoardDao();

    String pageParameter = request.getParameter(PAGE_PARAM);
    Integer currentPage = Optional.ofNullable(pageParameter)
            .map(p -> Integer.parseInt(p))
            .orElse(FIRST_PAGE);
    //한글 검색값 확인
    String searchKey = request.getParameter(KEY_PARAM);
    searchKey = Optional.ofNullable(searchKey)
            .filter(key -> !key.isEmpty())
            .orElse(FIND_ALL);
    String searchCategory = request.getParameter(CATEGORY_PARAM);
    searchCategory = Optional.ofNullable(searchCategory)
            .orElse(FIND_ALL);
    String searchStartDate = request.getParameter(START_DATE_PARAM);
    searchStartDate = Optional.ofNullable(searchStartDate)
            .orElse(DEFAULT_START_DATE);
    String searchEndDate = request.getParameter(END_DATE_PARAM);
    searchEndDate = Optional.ofNullable(searchEndDate)
            .orElse(DEFAULT_END_DATE);

    int totalCount =
            boardDao.getCountBySearchKeyAndCategoryAndDate(searchKey, searchCategory, searchStartDate, searchEndDate);
    List<BoardListDto> boardList =
            boardDao.getBoardListBySearchKeyAndCategoryAndDate(searchKey, searchCategory, searchStartDate, searchEndDate, currentPage, PAGE_OFFSET);

    ArrayList<String> pageLinkList = new ArrayList<>();
    pageLinkList.add("<font color=\"Fuchsia\">" + currentPage + "</font>&nbsp;\n");

    int previousPage = currentPage - 1;
    int nextPage = currentPage + 1;
    String searchParam = "?" +
            "search_category=" + searchCategory +
            "&search_key=" + searchKey +
            "&start_date=" + searchStartDate +
            "&end_date=" + searchEndDate;
    String searchUrl = "/view/list.jsp" + searchParam;
    while (pageLinkList.size() < 5) {
        //add left
        if (previousPage >= 0) {
            pageLinkList.add(0, "<a href=\"" + searchUrl + "&page=" + previousPage + "\">"+ previousPage +"</a>&nbsp;");
            previousPage--;
        }
        //add right
        if (nextPage * PAGE_OFFSET < totalCount) {
            pageLinkList.add("<a href=\"" + searchUrl + "&page=" + nextPage + "\">" + nextPage + "</a>&nbsp;");
            nextPage++;
        }

        if (previousPage < 0 && nextPage * PAGE_OFFSET >= totalCount) break;
    }




//    System.out.println("count = " + totalCount);
//    System.out.println("currentPage = " + currentPage);
//    System.out.println("searchKey = " + searchKey);
//    System.out.println("searchCategory = " + searchCategory);
//    System.out.println("searchStartDate = " + searchStartDate);
//    System.out.println("searchEndDate = " + searchEndDate);

%>
<html>
<head>
    <title>list view</title>
    <script type="text/javascript">

        function search(){
            let search_form = document.search_form
            let start_date = search_form.start_date.value
            let end_date = search_form.end_date.value
            let search_category = search_form.search_category.value
            let search_key = search_form.search_key.value

            console.log(start_date)
            console.log(end_date)
            console.log(search_category)
            console.log(search_key)

            search_form.action = "<%=cp%>/view/list.jsp";
            search_form.submit()
        }
    </script>
</head>
<body>
<div id="list">
    <div id="list_title">자유 게시판 - 목록</div>
    <div id="list_search">
        <form action="" method="get" name="search_form">
            <input type="date" name="start_date" value="2024-01-01" min="2023-01-01" max="2025-12-12">
            ~
            <input type="date" name="end_date" value="2025-01-01" min="2023-01-01" max="2025-12-12">
            <select name="search_category">
                <option value="all">all</option>
                <option value="java">java</option>
                <option value="javascript">javascript</option>
                <option value="database">database</option>
            </select>
            <input type="text" name="search_key"/>
            <input type="button" value=" 검 색 " onclick="search();"/>
        </form>
    </div>

    <div id="list_count">
        총 <%=totalCount%>건
    </div>

    <div id="main_list">
        <div id="title">
            <dl>
                <dt class="num">카테고리</dt>
                <dt class="subject">제목</dt>
                <dt class="name">작성자</dt>
                <dt class="created">조회수</dt>
                <dt class="hitCount">등록일시</dt>
                <dt class="hitCount">수정일시</dt>
            </dl>
        </div>

        <div id="lists">
            <%for(BoardListDto board : boardList){ %>
            <dl>
                <dd class="category"><%=board.getCategory().toString()%></dd>
                <dd class="category"><%=board.isHavaFile()%></dd>
                <dd class="title">
<%--                    <a href="<%=articleUrl %>&num=<%=dto.getNum()%>">--%>
                    <a href="/view/board.jsp<%=searchParam%>&page=<%=currentPage%>&board_id=<%=board.getBoardId()%>">
                        <%=board.getTitle() %>
                    </a>
                </dd>
                <dd class="name"><%=board.getName() %></dd>
                <dd class="view"><%=board.getView() %></dd>
                <dd class="created_at"><%=board.getCreatedAt() %></dd>
                <dd class="modified_at"><%=board.getModifiedAt() %></dd>
            </dl>
            <%} %>
        </div>
            <%=pageLinkList%>

        <div id="create_board_link">
            <button><a href="/view/create.jsp">등록</a></button>
        </div>
    </div>






</div>
</body>
</html>

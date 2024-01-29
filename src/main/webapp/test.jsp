<%@ page import="com.study.comment.dto.CommentCreateDto" %><%
    CommentCreateDto build = CommentCreateDto.builder()
            .board_id(1)
//            .content()
            .build();


    System.out.println("build = " + build);
    System.out.println("build = " + build);

%>
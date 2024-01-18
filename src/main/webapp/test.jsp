<%@ page import="java.sql.Connection" %>
<%@ page import="com.study.connection.ConnectionPool" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.io.FileInputStream" %><%

//    Connection connection = ConnectionPool.getConnection();
//
//    String testSql = "SELECT b.name, (SELECT (count(*) > 0) from file as f where f.board_id = b.board_id) AS count FROM board AS b";
//
//    Statement fileStatement = connection.createStatement();
//    fileStatement.execute(testSql);
//    fileStatement.close();

    request.setCharacterEncoding("UTF-8");

    String realPath = request.getRealPath("/files");

    File file = new File(realPath + "/스크린샷, 2024-01-16 21-55-17.png");

    String mimeType = request.getServletContext().getMimeType(file.toString());
    System.out.println("mimeType = " + mimeType);

    if(mimeType == null){
        response.setContentType("application.octec-stream");
    }

    String downName = null;
    String fileName = "가나다라.png";
    if(request.getHeader("user-agent").indexOf("MSIE") == -1)
    {
        downName = new String(fileName.getBytes("UTF-8"), "8859_1");
    }
    else
    {
        downName = new String(fileName.getBytes("EUC-KR"), "8859_1");
    }

    response.setHeader("Content-Disposition","attachment;filename=\"" + downName + "\";");


    FileInputStream inputStream = new FileInputStream(file);
    ServletOutputStream outputStream = response.getOutputStream();
    int numRead = 0;
    byte[] temp = new byte[1024*1024*10]; // 10M
    while((numRead = inputStream.read(temp,0,temp.length)) != -1){
        outputStream.write(temp,0,numRead); // temp배열에 있는 데이터의 0번째부터 최대 numRead만큼 출력한다.
    }

    outputStream.flush();
    outputStream.close();
    inputStream.close();
%>
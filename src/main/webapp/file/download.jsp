<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.File" %><%--
  Created by IntelliJ IDEA.
  User: woong
  Date: 24. 1. 17.
  Time: 오후 6:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    request.setCharacterEncoding("UTF-8");

    String fileName = request.getParameter("file_name");
    String originalFileName = request.getParameter("original_name");

    String realPath = request.getRealPath("/files");

    File file = new File(realPath + "/" + fileName);

    String mimeType = request.getServletContext().getMimeType(file.toString());
    System.out.println("mimeType = " + mimeType);

    if(mimeType == null){
        response.setContentType("application.octec-stream");
    }

    if(request.getHeader("user-agent").indexOf("MSIE") == -1)
    {
        originalFileName = new String(originalFileName.getBytes("UTF-8"), "8859_1");
    }
    else
    {
        originalFileName = new String(originalFileName.getBytes("EUC-KR"), "8859_1");
    }

    response.setHeader("Content-Disposition","attachment;filename=\"" + originalFileName + "\";");


    FileInputStream inputStream = new FileInputStream(file);
    ServletOutputStream outputStream = response.getOutputStream();
    int numRead = 0;
    byte[] temp = new byte[1024*1024*10]; //
    while((numRead = inputStream.read(temp,0,temp.length)) != -1){
        outputStream.write(temp,0,numRead); //
    }

    outputStream.flush();
    outputStream.close();
    inputStream.close();
%>
package com.study.service;

import com.study.exception.WrapCheckedException;
import com.study.file.dao.FileDao;
import com.study.file.dao.FileDaoFactory;
import com.study.file.dto.FileDownloadDto;
import com.study.filter.RequestHandler;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * file의 business logic을 담당
 */
public class FileService {
    private final FileDao fileDao;
    private final RequestHandler requestHandler;

    public FileService() {
        this.fileDao = FileDaoFactory.getDao();
        this.requestHandler = new RequestHandler();
    }

    /**
     * download file
     *
     * @param request
     * @param response
     */
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        int fileId = requestHandler.getFileId(request);

        FileDownloadDto fileDto = fileDao.getFileByFileId(fileId)
                .orElseThrow(() -> new IllegalArgumentException("invalid fileId"));

        File file = new File(fileDto.getPath());

        String mimeType = request.getServletContext().getMimeType(file.toString());
        System.out.println("mimeType = " + mimeType);

        if(mimeType == null){
            response.setContentType("application.octec-stream");
        }

        try {
            String originalFileName = fileDto.getOriginalName();
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
        } catch (IOException e) {
            throw new WrapCheckedException("file download Exception", e);
        }

    }
}

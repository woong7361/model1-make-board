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

import static com.study.constant.ExceptionConstant.FILE_DOWNLOAD_EXCEPTION_MESSAGE;
import static com.study.constant.ExceptionConstant.INVALID_FILE_ID_MESSAGE;

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
     * file을 실제 다운로드 한다.
     *
     * @param request http request
     * @param response http response
     */
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        int fileId = requestHandler.getFileId(request);

        FileDownloadDto fileDto = fileDao.getFileDownloadDtoByFileId(fileId)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_FILE_ID_MESSAGE));

        File file = new File(fileDto.getPath() + "/" + fileDto.getName());

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
            throw new WrapCheckedException(FILE_DOWNLOAD_EXCEPTION_MESSAGE, e);
        }

    }
}

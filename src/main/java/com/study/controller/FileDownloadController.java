package com.study.controller;

import com.study.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * file 다운로드 컨트롤러
 */
public class FileDownloadController implements Controller{

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        FileService fileService = new FileService();
        fileService.downloadFile(request, response);
    }
}

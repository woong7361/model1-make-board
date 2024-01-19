package com.study.filter.multitpart;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MultipartHandler {

    public static final String SAVE_DIR = "/files";
    public static final int MAX_FILE_SIZE = 1024 * 1024 * 5;
    public static final String UTF_8_ENCODE_TYPE = "UTF-8";

    public MultipartRequest getMultipartRequest(HttpServletRequest request) {
        String saveDirectoryPath = request.getSession().getServletContext().getRealPath(SAVE_DIR);
        FileRenamePolicy policy = new UniqueFileNamePolicy();

        try {
            return new MultipartRequest(request, saveDirectoryPath, MAX_FILE_SIZE, UTF_8_ENCODE_TYPE, policy);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("MULTIPART IO EXCEPTION");
        }
    }
}

package com.study.filter.multitpart;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import com.study.exception.WrapCheckedException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.study.config.ConfigConst.*;

/**
 * multipartRequest의 생성을 책임진다.
 */
public class MultipartHandler {

    public MultipartRequest getMultipartRequest(HttpServletRequest request) {
        String saveDirectoryPath = request.getSession().getServletContext().getRealPath(SAVE_DIR);
        FileRenamePolicy policy = new UniqueFileNamePolicy();

        try {
            return new MultipartRequest(request, saveDirectoryPath, MAX_FILE_SIZE, MULTIPART_ENCODE_TYPE, policy);
        } catch (IOException e) {
            throw new WrapCheckedException("MULTIPART IO EXCEPTION", e);
        }
    }
}

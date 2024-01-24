package com.study.filter.multitpart;

import com.oreilly.servlet.multipart.FileRenamePolicy;
import com.study.exception.WrapCheckedException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 동시성이 보장되는 파일 이름 생성 정책
 */
public class UniqueFileNamePolicy implements FileRenamePolicy {

    /**
     * 파일 이름을 재정의한다.
     * @param file
     * @return millisecond + UUID + extension으로 구성된 새로운 파일 이름을 갖는 파일을 반환한다.
     */
    public File rename(File file) {
        String originalFileExtension = getFileExtension(file.getName());
        String newFileName = createNewFilename(originalFileExtension);

        File newFile = new File(file.getParent(), newFileName);
        createNewFile(newFile);

        return newFile;
    }

//----------------------------------------------------------------------------------------------------------------------
    private String createNewFilename(String originalFileExtension) {
        return System.nanoTime() + UUID.randomUUID().toString() + "." + originalFileExtension;
    }

    private String getFileExtension(String originalName) {
        String[] split = originalName.split("[.]");
        return originalName.split("[.]")[split.length - 1];

    }

    private void createNewFile(File f) {
        try {
            f.createNewFile();
        } catch (IOException e) {
            throw new WrapCheckedException("file create Error", e);
        }
    }
}

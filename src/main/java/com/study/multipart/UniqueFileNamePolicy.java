package com.study.multipart;

import com.oreilly.servlet.multipart.FileRenamePolicy;

import java.io.File;
import java.io.IOException;

public class UniqueFileNamePolicy implements FileRenamePolicy {

    public File rename(File file) {
        String originalFileExtension = this.getFileExtension(file.getName());
        String newFileName = System.nanoTime() + "." + originalFileExtension;

        File newFile = new File(file.getParent(), newFileName);
        createNewFile(newFile);

        return newFile;
    }

    private String getFileExtension(String orginalName) {
        String[] split = orginalName.split("[.]");
        return orginalName.split("[.]")[split.length - 1];

    }

    private void createNewFile(File f) {
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("file name system Error");
        }
    }
}

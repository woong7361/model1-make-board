package com.study.util;

import java.io.File;
import java.util.List;

/**
 * real file handler class
 */
public class FileHandler {

    /**
     * delete file
     * @param filePath file path
     * @param fileName real file name
     */
    public static void deleteFile(String filePath, String fileName) {
        File file = new File(filePath + "/" + fileName);
        file.delete();
    }
    /**
     * delete file
     * @param fullPath file full path include file name
     */
    public static void deleteFile(String fullPath) {
        File file = new File(fullPath);
        file.delete();
    }

    /**
     * delete file list
     * @param fileFullPathList file full path list include file name
     */
    public static void deleteFiles(List<String> fileFullPathList) {
        for (String fullPath : fileFullPathList) {
            deleteFile(fullPath);
        }
    }
}

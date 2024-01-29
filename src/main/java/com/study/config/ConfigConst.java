package com.study.config;

/**
 * 설정값 상수 클래스
 * TODO 환경변수로 숨기기 필요
 */
public class ConfigConst {

    //----------DB Module-----------
    //TODO aa
    public static final String Data_Access_Module = "jdbc";
    public static final String JDBC = "jdbc";

    //------------paging-------------
    public static final int PAGE_OFFSET = 3;

    //-------------DB---------------
    public static final String DB_URL = "jdbc:mysql://localhost:3306/study";
    public static final String USER = "woong";
    public static final String PASSWORD = "woong7361";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    //-------------multipart---------------
    public static final String SAVE_DIR = "/files";
    public static final int MAX_FILE_SIZE = 1024 * 1024 * 5;
    public static final String MULTIPART_ENCODE_TYPE = "UTF-8";

    //-------------encryption---------------
    public static final String ALGORITHM = "AES";
    public static final String KEY = "secret_key_12345";
}

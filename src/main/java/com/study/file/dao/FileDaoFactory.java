package com.study.file.dao;

import com.study.exception.CustomException;

import static com.study.config.ConfigConst.*;

/**
 * file data access object factory class
 */
public class FileDaoFactory {

    /**
     * file dao를 반환한다.
     * @return 사용하는 data access module에 따른 doa를 반환한다.
     */
    public static FileDao getDao() {
        if (Data_Access_Module.equals(JDBC)) {
            return new JdbcFileDao();
        } else {
            throw new CustomException("not Exist Dao Type");
        }
    }

}

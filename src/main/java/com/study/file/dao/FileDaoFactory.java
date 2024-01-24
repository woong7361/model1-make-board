package com.study.file.dao;

import com.study.exception.CustomException;

import static com.study.config.ConfigConst.*;

public class FileDaoFactory {
    public static FileDao getDao() {
        if (Data_Access_Module.equals(JDBC)) {
            return new JdbcFileDao();
        } else {
            throw new CustomException("not Exist Dao Type");
        }
    }

}

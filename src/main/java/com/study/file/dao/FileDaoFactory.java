package com.study.file.dao;

import com.study.constant.DaoTypeConst;
import com.study.exception.CustomException;

import static com.study.config.ConfigConst.Data_Access_Module;

public class FileDaoFactory {
    public static FileDao getDao() {
        if (Data_Access_Module.equals(DaoTypeConst.JDBC)) {
            return new JdbcFileDao();
        } else {
            throw new CustomException("not Exist Dao Type");
        }
    }

}

package com.study.board.dao;

import com.study.constant.DaoTypeConst;
import com.study.exception.CustomException;

import static com.study.config.ConfigConst.Data_Access_Module;

public class BoardDaoFactory {
    public static BoardDao getDao() {
        if (Data_Access_Module.equals(DaoTypeConst.JDBC)) {
            return new JdbcBoardDao();
        } else {
            throw new CustomException("not Exist Dao Type");
        }
    }

}

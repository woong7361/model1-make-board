package com.study.board.dao;

import com.study.exception.CustomException;

import static com.study.config.ConfigConst.*;

public class BoardDaoFactory {
    public static BoardDao getDao() {
        if (Data_Access_Module.equals(JDBC)) {
            return new JdbcBoardDao();
        } else {
            throw new CustomException("not Exist Dao Type");
        }
    }

}

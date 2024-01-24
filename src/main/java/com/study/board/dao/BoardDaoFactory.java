package com.study.board.dao;

import com.study.exception.CustomException;

import static com.study.config.ConfigConst.*;

/**
 * boardDao를 반환하는 factory class
 */
public class BoardDaoFactory {

    /**
     * board dao를 반환한다.
     * @return 사용하는 data access module에 따른 doa를 반환한다.
     */
    public static BoardDao getDao() {
        if (Data_Access_Module.equals(JDBC)) {
            return new JdbcBoardDao();
        } else {
            throw new CustomException("not Exist Dao Type");
        }
    }

}

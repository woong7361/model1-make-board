package com.study.comment.dao;

import com.study.constant.DaoTypeConst;
import com.study.exception.CustomException;

import static com.study.config.ConfigConst.Data_Access_Module;

public class CommentDaoFactory {
    public static CommentDao getDao() {
        if (Data_Access_Module.equals(DaoTypeConst.JDBC)) {
            return new JdbcCommentDao();
        } else {
            throw new CustomException("not Exist Dao Type");
        }
    }

}

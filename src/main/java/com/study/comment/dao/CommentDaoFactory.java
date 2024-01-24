package com.study.comment.dao;

import com.study.exception.CustomException;

import static com.study.config.ConfigConst.*;

public class CommentDaoFactory {
    public static CommentDao getDao() {
        if (Data_Access_Module.equals(JDBC)) {
            return new JdbcCommentDao();
        } else {
            throw new CustomException("not Exist Dao Type");
        }
    }

}

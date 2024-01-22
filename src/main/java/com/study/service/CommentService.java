package com.study.service;

import com.study.comment.dao.CommentDao;
import com.study.comment.dao.CommentDaoFactory;
import com.study.comment.dto.CommentCreateDto;
import com.study.exception.WrapCheckedException;
import com.study.filter.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.study.constant.ControllerUriConstant.BOARD_VIEW_CONTROLLER_URI;

public class CommentService {
    private final CommentDao commentDao;
    private final RequestHandler requestHandler;

    public CommentService() {
        this.commentDao = CommentDaoFactory.getDao();
        this.requestHandler = new RequestHandler();
    }


    public void createComment(HttpServletRequest request, HttpServletResponse response) {
        CommentCreateDto commentCreateDto = requestHandler.getCommentCreateDto(request);

        commentDao.saveComment(commentCreateDto);

        forward(request, response, BOARD_VIEW_CONTROLLER_URI);
    }

//    ------------------------------------------------------------------------------------------------------------------
    private void forward(HttpServletRequest request, HttpServletResponse response, String uri) {
        try {
            request.getRequestDispatcher(uri).forward(request, response);
        } catch (ServletException e) {
            throw new WrapCheckedException("ServletException", e);
        } catch (IOException e) {
            throw new WrapCheckedException("IOException", e);
        }
    }
}
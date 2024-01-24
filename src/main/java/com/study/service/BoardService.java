package com.study.service;

import com.study.board.dao.BoardDao;
import com.study.board.dao.BoardDaoFactory;
import com.study.board.dto.*;
import com.study.comment.dao.CommentDao;
import com.study.comment.dao.CommentDaoFactory;
import com.study.comment.dto.CommentDto;
import com.study.exception.CustomException;
import com.study.exception.WrapCheckedException;
import com.study.file.dao.FileDao;
import com.study.file.dao.FileDaoFactory;
import com.study.file.dto.FileDto;
import com.study.filter.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.study.config.ConfigConst.PAGE_OFFSET;
import static com.study.constant.ControllerUriConstant.BOARD_LIST_VIEW_CONTROLLER_URI;
import static com.study.constant.ControllerUriConstant.BOARD_VIEW_CONTROLLER_URI;
import static com.study.constant.ViewUriConstant.*;

/**
 * board의 business logic을 담당
 */
public class BoardService {
    private final BoardDao boardDao;
    private final FileDao fileDao;
    private final CommentDao commentDao;
    private final RequestHandler requestHandler;

    public BoardService() {
        this.boardDao = BoardDaoFactory.getDao();
        this.fileDao = FileDaoFactory.getDao();
        this.commentDao = CommentDaoFactory.getDao();
        this.requestHandler = new RequestHandler();
    }

    /**
     * 게시글을 생성후 게시글 페이지로 forward 한다.
     *
     * @param request http request
     * @param response http response
     */
    public void write(HttpServletRequest request, HttpServletResponse response) {
        BoardCreateDto boardCreateDto = requestHandler.getBoardCreateDto(request);

        int boardId = createBoard(boardCreateDto);

        // TODO make uri const
        forward(request, response,  BOARD_VIEW_CONTROLLER_URI + "?board_id=" + boardId);
    }

    /**
     * 게시글 view를 위한 모델을 생성후 forward 한다.
     *
     * <p>
     *     model을 생성하고 viewcount를 올려준다
     * </p>
     * @param request http request
     * @param response http response
     */
    public void boardView(HttpServletRequest request, HttpServletResponse response) {
        int boardId = requestHandler.getBoardId(request);

        BoardDto boardDto = boardDao.getBoardByBoardId(boardId)
                .orElseThrow(() -> new CustomException("not exist board"));
        List<FileDto> fileDtoList = fileDao.getFileDtoByBoardId(boardId);
        List<CommentDto> commentList = commentDao.getCommentByBoardId(boardId);
        boardDao.addBoardViewByBoardId(boardId);

        request.setAttribute("boardDto", boardDto);
        request.setAttribute("fileDtoList", fileDtoList);
        request.setAttribute("commentList", commentList);

        forward(request, response, BOARD_VIEW_URI);

    }

    /**
     * 게시글 list view를 위한 모델을 생성후 forward 한다.
     *
     * <p>
     *     들어온 검색 조건과 pageOffset을 통해 보여줄 board list를 paging한다.
     * </p>
     *
     * @param request http request
     * @param response http response
     */
    public void listView(HttpServletRequest request, HttpServletResponse response) {
        BoardSearchDto boardSearchDto = requestHandler.getBoardSearchDto(request);
        int currentPage = requestHandler.getCurrentPage(request);

        int totalCount = boardDao.getCountBySearchParam(boardSearchDto);
        List<BoardListDto> boardList = boardDao.getBoardListBySearchParam(boardSearchDto, currentPage, PAGE_OFFSET);

        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("boardList", boardList);

        forward(request, response, BOARD_LIST_VIEW_URI);
    }

    /**
     * 게시글 update view를 위한 모델을 생성후 forward한다.
     *
     * @param request http request
     * @param response http response
     */
    public void updateBoardView(HttpServletRequest request, HttpServletResponse response) {
        int boardId = requestHandler.getBoardId(request);

        BoardDto boardDto = boardDao.getBoardByBoardId(boardId).
                orElseThrow(() -> new IllegalArgumentException("not exist board"));
        List<FileDto> fileList = fileDao.getFileDtoByBoardId(boardId);

        request.setAttribute("boardId", boardId);
        request.setAttribute("boardDto", boardDto);
        request.setAttribute("fileList", fileList);

        forward(request, response, BOARD_UPDATE_VIEW_URI);
    }

    /**
     * update board and forward
     *
     * @param request http request
     * @param response http response
     */
    public void updateBoard(HttpServletRequest request, HttpServletResponse response) {
        RequestHandler requestHandler = new RequestHandler();
        BoardModifyDto boardModifyDto = requestHandler.getBoardModifyDto(request);

        boardDao.updateBoard(boardModifyDto);

        List<String> filePathList = fileDao.getFilePathListByIdList(boardModifyDto.getDeleteFileIdList());
        fileDao.deleteFileByListIdList(boardModifyDto.getDeleteFileIdList());
        fileDao.saveFileList(boardModifyDto.getCreateFileList(), boardModifyDto.getBoard_id());

        //TODO filehandler로 추출
        for (String path : filePathList) {
            File file = new File(path);
            file.delete();
        }

        forward(request,response, BOARD_VIEW_CONTROLLER_URI);
    }

    /**
     * delete board and forward
     *
     * @param request http request
     * @param response http response
     */
    public void deleteBoard(HttpServletRequest request, HttpServletResponse response) {
        int boardId = requestHandler.getBoardId(request);

        fileDao.deleteByBoardId(boardId);
        commentDao.deleteByBoardId(boardId);

        List<String> filePathList = fileDao.getFilePathListByBoardId(boardId);
        for (String path : filePathList) {
            File file = new File(path);
            file.delete();
        }

        boardDao.deleteByBoardId(boardId);

        forward(request,response, BOARD_LIST_VIEW_CONTROLLER_URI);
    }

    /**
     * forward to board write
     *
     * @param request http request
     * @param response http response
     */
    public void WriteView(HttpServletRequest request, HttpServletResponse response) {
        forward(request, response, BOARD_WRITE_VIEW_URI);
    }

//    ------------------------------------------------------------------------------------------------------------------

    private int createBoard(BoardCreateDto boardCreateDto) {
        int boardId = boardDao.saveBoard(boardCreateDto);
        fileDao.saveFileList(boardCreateDto.getFileList(), boardId);

        return boardId;
    }

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

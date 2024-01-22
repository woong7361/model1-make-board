package com.study.service;

import com.study.board.dao.BoardDao;
import com.study.board.dao.BoardDaoFactory;
import com.study.board.dto.BoardCreateDto;
import com.study.file.dao.FileDao;
import com.study.file.dao.FileDaoFactory;
import com.study.filter.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.study.constant.ViewUriConstant.BOARD_VIEW_URI;

/**
 * board의 business logic을 담당한다
 */
public class BoardService {
    private final BoardDao boardDao;
    private final FileDao fileDao;
    private final RequestHandler requestHandler;

    public BoardService() {
        this.boardDao = BoardDaoFactory.getDao();
        this.fileDao = FileDaoFactory.getDao();
        this.requestHandler = new RequestHandler();
    }

    /**
     * create board
     *
     * @param request
     * @param response
     */
    public void write(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BoardCreateDto boardCreateDto = requestHandler.getBoardCreateDto(request);

        int boardId = createBoard(boardCreateDto);

        // TODO make uri const
        response.sendRedirect(BOARD_VIEW_URI + "?board_id=" + boardId);
    }

    public void view(HttpServletRequest request, HttpServletResponse response) {

//        response.sendRedirect(BOARD_VIEW_URI);
    }

//    ------------------------------------------------------------------------------------------------------------------

    private int createBoard(BoardCreateDto boardCreateDto) throws SQLException {
        int boardId = boardDao.saveBoard(boardCreateDto);
        fileDao.saveFileListIdList(boardCreateDto.getFileList(), boardId);

        return boardId;
    }

}

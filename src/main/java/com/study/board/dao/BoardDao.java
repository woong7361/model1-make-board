package com.study.board.dao;

import com.study.board.dto.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BoardDao {
    int saveBoard(BoardCreateDto boardCreateDto);

    int getCountBySearchParam(BoardSearchDto boardSearchDto);

    List<BoardListDto> getBoardListBySearchParam(BoardSearchDto boardSearchDto, Integer currentPage, int pageOffset);

    Optional<BoardDto> getBoardByBoardId(int boardId);

    void addBoardViewByBoardId(int boardId);

    void updateBoard(BoardModifyDto boardModifyDto);

    void deleteByBoardId(int boardId) throws SQLException;
}

package com.study.board.dao;

import com.study.board.dto.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BoardDao {
    int saveBoard(BoardCreateDto boardCreateDto) throws SQLException;

    int getCountBySearchParam(BoardSearchDto boardSearchDto) throws SQLException;

    List<BoardListDto> getBoardListBySearchParam(BoardSearchDto boardSearchDto, Integer currentPage, int pageOffset) throws SQLException;

    Optional<BoardDto> getBoardByBoardId(int boardId) throws SQLException;

    void addBoardViewByBoardId(int boardId) throws SQLException;

    void updateBoard(BoardModifyDto boardModifyDto) throws SQLException;

    void deleteByBoardId(int boardId) throws SQLException;
}

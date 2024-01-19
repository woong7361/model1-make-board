package com.study.board.dao;

import com.study.board.dto.*;

import java.util.List;
import java.util.Optional;

public interface BoardDao {
    int saveBoard(BoardCreateDto boardCreateDto) throws Exception;

    int getCountBySearchParam(BoardSearchDto boardSearchDto) throws Exception;

    List<BoardListDto> getBoardListBySearchParam(BoardSearchDto boardSearchDto, Integer currentPage, int pageOffset) throws Exception;

    Optional<BoardDto> getBoardByBoardId(int boardId) throws Exception;

    void addBoardViewByBoardId(int boardId) throws Exception;

    void updateBoard(BoardModifyDto boardModifyDto) throws Exception;

    void deleteByBoardId(int boardId) throws Exception;
}

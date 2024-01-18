package com.study.board;

import com.study.board.dto.BoardCreateDto;
import com.study.board.dto.BoardDto;
import com.study.board.dto.BoardListDto;
import com.study.board.dto.BoardModifyDto;

import java.util.List;
import java.util.Optional;

public interface BoardDao {
    int saveBoard(BoardCreateDto boardCreateDto) throws Exception;

    int getCountBySearchKeyAndCategoryAndDate(String searchKey, Category searchCategory, String searchStartDate, String searchEndDate) throws Exception;

    List<BoardListDto> getBoardListBySearchKeyAndCategoryAndDate(String searchKey, Category searchCategory, String searchStartDate, String searchEndDate, Integer currentPage, int pageOffset) throws Exception;

    Optional<BoardDto> getBoardByBoardId(int boardId) throws Exception;

    void addBoardViewByBoardId(int boardId) throws Exception;

    void updateBoard(BoardModifyDto boardModifyDto) throws Exception;

    void deleteByBoardId(int boardId) throws Exception;
}

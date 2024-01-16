package com.study.board;

import java.util.List;

public interface BoardDao {
    void saveBoard(BoardCreateDto boardCreateDto) throws Exception;

    int getCountBySearchKeyAndCategoryAndDate(String searchKey, String searchCategory, String searchStartDate, String searchEndDate) throws Exception;

    List<BoardListDto> getBoardListBySearchKeyAndCategoryAndDate(String searchKey, String searchCategory, String searchStartDate, String searchEndDate, Integer currentPage, int pageOffset) throws Exception;
}

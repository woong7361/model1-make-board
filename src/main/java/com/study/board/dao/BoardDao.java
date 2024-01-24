package com.study.board.dao;

import com.study.board.dto.*;

import java.util.List;
import java.util.Optional;

/**
 * board data access object
 */
public interface BoardDao {

    /**
     * 저장공간에 board를 저장한다.
     * @param boardCreateDto
     * @return 저장한 board의 PK를 반환한다.
     */
    int saveBoard(BoardCreateDto boardCreateDto);

    /**
     * 저장공간에서 검색조건에 따라 board의 count를 계산하여 가져온다.
     * @param boardSearchDto
     * @return 검색 조건에 따른 board의 총 count를 반환한다.
     */
    int getCountBySearchParam(BoardSearchDto boardSearchDto);

    /**
     * 검색조건에 맞춰 board paging의 결과값을 가져온다.
     * @param boardSearchDto
     * @param currentPage
     * @param pageOffset 한 페이지당 board 갯수
     * @return 검색조건에 맞는 board list를 반환한다.
     */
    List<BoardListDto> getBoardListBySearchParam(BoardSearchDto boardSearchDto, Integer currentPage, int pageOffset);

    /**
     * boardId에 맞는 board를 반환한다.
     * @param boardId
     * @return boardDto
     */
    Optional<BoardDto> getBoardByBoardId(int boardId);

    /**
     * boar의 view를 1늘린다.
     * @param boardId
     */
    void addBoardViewByBoardId(int boardId);

    /**
     * board를 update한다.
     * @param boardModifyDto
     */
    void updateBoard(BoardModifyDto boardModifyDto);

    /**
     * boardId에 해당하는 board를 삭제한다.
     * @param boardId
     */
    void deleteByBoardId(int boardId);
}

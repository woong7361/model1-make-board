package com.study.board.dao;


import com.study.board.Category;
import com.study.board.dto.*;
import com.study.connection.ConnectionPool;
import com.study.encryption.CipherEncrypt;
import com.study.encryption.EncryptManager;
import com.study.exception.WrapCheckedException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcBoardDao implements BoardDao{
    public static final String FIND_ALL = "ALL";

    @Override
    public int saveBoard(BoardCreateDto boardCreateDto) {

        String createBoardSql = "INSERT INTO board (" +
                "category_id, name, password, title, " +
                "content, view, created_at, modified_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(createBoardSql, Statement.RETURN_GENERATED_KEYS);

        ) {
            EncryptManager encryptManger = new CipherEncrypt();
            String password = encryptManger.encrypt(boardCreateDto.getPassword());

            preparedStatement.setInt(1, boardCreateDto.getCategory().getCategoryId());
            preparedStatement.setString(2, boardCreateDto.getName());
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, boardCreateDto.getTitle());
            preparedStatement.setString(5, boardCreateDto.getContent());
            preparedStatement.setInt(6, 0);
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();

            int boardId = 0;
            ResultSet rs = preparedStatement.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
            if (rs.next()) {
                boardId = rs.getInt(1); // 키값 초기화
            }

            return boardId;
        } catch (SQLException e) {
            throw new WrapCheckedException("sql Exception", e);
        }
    }

    @Override
    public int getCountBySearchParam(BoardSearchDto boardSearchDto){
        String getCountSql = "SELECT COUNT(*) FROM board WHERE (created_at BETWEEN ? AND ?)" ;

        String categorySearchSql = "";
        if (!boardSearchDto.getSearchCategory().equals(Category.ALL)){
            categorySearchSql = " AND (category_id = ?)";
        }
        String searchKeySql = "";
        if (!boardSearchDto.getSearchKey().equals(FIND_ALL)) {
            searchKeySql = " AND (title LIKE ? OR name LIKE ? OR content LIKE ?)";
        }
        getCountSql = getCountSql + categorySearchSql + searchKeySql;

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getCountSql);
        ) {
            int index = 1;
            preparedStatement.setTimestamp(index++, Timestamp.valueOf(boardSearchDto.getSearchStartDate()));
            preparedStatement.setTimestamp(index++, Timestamp.valueOf(boardSearchDto.getSearchEndDate()));

            if (!boardSearchDto.getSearchCategory().equals(Category.ALL)) {
                preparedStatement.setInt(index++, boardSearchDto.getSearchCategory().getCategoryId());
            }
            if (!boardSearchDto.getSearchKey().equals(FIND_ALL)){
                String searchKey = boardSearchDto.getSearchKey();
                preparedStatement.setString(index++, "%"+searchKey+"%");
                preparedStatement.setString(index++, "%"+searchKey+"%");
                preparedStatement.setString(index++, "%"+searchKey+"%");
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            return count;
        } catch (SQLException e) {
            throw new WrapCheckedException("sql Exception", e);
        }
    }

    @Override
    public List<BoardListDto> getBoardListBySearchParam(BoardSearchDto boardSearchDto, Integer currentPage, int pageOffset) {
        String getBoardListSql = "SELECT b.*, c.category, (SELECT (count(*) > 0) from file as f where f.board_id = b.board_id) AS count " +
                "FROM board AS b LEFT JOIN category AS c ON b.category_id = c.category_id " +
                "WHERE (created_at BETWEEN ? AND ?)" ;

        String categorySearchSql = "";
        if (!boardSearchDto.getSearchCategory().equals(Category.ALL)){
            categorySearchSql = " AND (b.category_id = ?)";
        }
        String searchKeySql = "";
        if (!boardSearchDto.getSearchKey().equals(FIND_ALL)) {
            searchKeySql = " AND (title LIKE ? OR name LIKE ? OR content LIKE ?)";
        }
        String pageSql = " ORDER BY board_id DESC LIMIT ?, ?";
        getBoardListSql = getBoardListSql + categorySearchSql + searchKeySql + pageSql;

        int index = 1;

        try(
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getBoardListSql);
        ) {
            preparedStatement.setTimestamp(index++, Timestamp.valueOf(boardSearchDto.getSearchStartDate()));
            preparedStatement.setTimestamp(index++, Timestamp.valueOf(boardSearchDto.getSearchEndDate()));

            if (!boardSearchDto.getSearchCategory().equals(Category.ALL)) {
                preparedStatement.setInt(index++, boardSearchDto.getSearchCategory().getCategoryId());
            }
            if (!boardSearchDto.getSearchKey().equals(FIND_ALL)){
                String searchKey = boardSearchDto.getSearchKey();
                preparedStatement.setString(index++, "%"+searchKey+"%");
                preparedStatement.setString(index++, "%"+searchKey+"%");
                preparedStatement.setString(index++, "%"+searchKey+"%");
            }

            preparedStatement.setInt(index++, currentPage * pageOffset);
            preparedStatement.setInt(index++, pageOffset);


            ResultSet resultSet = preparedStatement.executeQuery();

            List<BoardListDto> boardList = new ArrayList<>();
            while (resultSet.next()) {
                BoardListDto board = new BoardListDto(
                        resultSet.getInt("b.board_id"),
                        Category.valueOf(resultSet.getString("c.category")),
                        resultSet.getString("b.title"),
                        resultSet.getString("b.name"),
                        resultSet.getInt("b.view"),
                        resultSet.getTimestamp("b.created_at").toLocalDateTime(),
                        resultSet.getTimestamp("b.modified_at").toLocalDateTime(),
                        resultSet.getBoolean("count")
                );
                boardList.add(board);
            }

            return boardList;
        }catch (SQLException e){
            throw new WrapCheckedException("sql Exception", e);
        }
    }

    @Override
    public Optional<BoardDto> getBoardByBoardId(int boardId){

        String getBoardSql =
                "SELECT b.*, (SELECT category FROM category AS c WHERE b.category_id = c.category_id) AS category " +
                "FROM board AS b " +
                "WHERE (b.board_id = ?)" ;

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getBoardSql);
        ) {
            preparedStatement.setInt(1, boardId);

            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<BoardDto> boardDto = Optional.empty();
            if (resultSet.next()) {
                boardDto = Optional.of(new BoardDto(
                        resultSet.getInt("b.board_id"),
                        Category.valueOf(resultSet.getString("category")),
                        resultSet.getString("b.title"),
                        resultSet.getString("b.name"),
                        resultSet.getString("b.content"),
                        resultSet.getInt("b.view"),
                        resultSet.getTimestamp("b.created_at").toLocalDateTime(),
                        resultSet.getTimestamp("b.modified_at").toLocalDateTime()
                ));
            }
            return boardDto;

        } catch (SQLException sqlException) {
            throw new WrapCheckedException("sql exception", sqlException);
        }
//        Connection connection = ConnectionPool.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(getBoardSql);
//        preparedStatement.setInt(1, boardId);
//
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        Optional<BoardDto> boardDto = Optional.empty();
//        if (resultSet.next()) {
//            boardDto = Optional.of(new BoardDto(
//                    resultSet.getInt("b.board_id"),
//                    Category.valueOf(resultSet.getString("category")),
//                    resultSet.getString("b.title"),
//                    resultSet.getString("b.name"),
//                    resultSet.getString("b.content"),
//                    resultSet.getInt("b.view"),
//                    resultSet.getTimestamp("b.created_at").toLocalDateTime(),
//                    resultSet.getTimestamp("b.modified_at").toLocalDateTime()
//            ));
//        }
//
//        resultSet.close();
//        preparedStatement.close();
//        connection.close();

//        return boardDto;
    }

    @Override
    public void addBoardViewByBoardId(int boardId) {
        String getBoardSql = "UPDATE board SET view = (view+1) WHERE board_id = ?";

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getBoardSql);
        ) {
            preparedStatement.setInt(1, boardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new WrapCheckedException("sql Exception", e);
        }
    }

    @Override
    public void updateBoard(BoardModifyDto boardModifyDto) throws SQLException {
        Connection connection = ConnectionPool.getConnection();

        String updateBoardSql = "UPDATE board " +
                "SET name = ?, " +
                "password = ?, " +
                "title = ?, " +
                "content = ? ," +
                "modified_at = ? " +
                "WHERE board_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(updateBoardSql);
        preparedStatement.setString(1, boardModifyDto.getName());
        preparedStatement.setString(2, boardModifyDto.getPassword());
        preparedStatement.setString(3, boardModifyDto.getTitle());
        preparedStatement.setString(4, boardModifyDto.getContent());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setInt(6, boardModifyDto.getBoard_id());
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    @Override
    public void deleteByBoardId(int boardId) throws SQLException {
        Connection connection = ConnectionPool.getConnection();
        String deleteSql = "DELETE FROM board WHERE board_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
        preparedStatement.setInt(1, boardId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

}

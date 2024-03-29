package com.study.board.dao;


import com.study.board.Category;
import com.study.board.dto.*;
import com.study.connection.DBConnection;
import com.study.encryption.CipherEncrypt;
import com.study.encryption.EncryptManager;
import com.study.exception.WrapCheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.study.constant.ExceptionConstant.SQL_EXCEPTION_MESSAGE;

public class JdbcBoardDao implements BoardDao{
    public static final String FIND_ALL = "ALL";
    private final Logger logger = LoggerFactory.getLogger(JdbcBoardDao.class);

    @Override
    public int saveBoard(BoardCreateDto boardCreateDto) {

        String createBoardSql = "INSERT INTO board " +
                "(category_id, name, password, title, " +
                "content, view, created_at, modified_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
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
            logger.error("sql: {}", createBoardSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public int getCountBySearchParam(BoardSearchDto boardSearchDto){
        String getCountSql =
                "SELECT COUNT(*) " +
                "FROM board " +
                "WHERE (created_at BETWEEN ? AND ?)" ;

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
                Connection connection = DBConnection.getConnection();
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
            logger.error("sql: {}", getCountSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<BoardListDto> getBoardListBySearchParam(BoardSearchDto boardSearchDto, Integer currentPage, int pageOffset) {
        String getBoardListSql =
                "SELECT b.*, c.category, " +
                        "(SELECT (count(*) > 0) from file as f where f.board_id = b.board_id) AS count " +
                "FROM board AS b " +
                "LEFT JOIN category AS c ON b.category_id = c.category_id " +
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
                Connection connection = DBConnection.getConnection();
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
                BoardListDto board = BoardListDto.builder()
                        .boardId(resultSet.getInt("b.board_id"))
                        .category(Category.valueOf(resultSet.getString("c.category")))
                        .title(resultSet.getString("b.title"))
                        .name(resultSet.getString("b.name"))
                        .view(resultSet.getInt("b.view"))
                        .createdAt(resultSet.getTimestamp("b.created_at").toLocalDateTime())
                        .modifiedAt(resultSet.getTimestamp("b.modified_at").toLocalDateTime())
                        .havaFile(resultSet.getBoolean("count"))
                        .build();

                boardList.add(board);
            }

            return boardList;
        }catch (SQLException e){
            logger.error("sql: {}", getBoardListSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<BoardDto> getBoardByBoardId(int boardId){
        String getBoardSql =
                "SELECT b.*, " +
                        "(SELECT category FROM category AS c WHERE b.category_id = c.category_id) AS category " +
                "FROM board AS b " +
                "WHERE (b.board_id = ?)" ;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getBoardSql);
        ) {
            preparedStatement.setInt(1, boardId);

            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<BoardDto> boardDto = Optional.empty();
            if (resultSet.next()) {
                boardDto = Optional.of(
                        BoardDto.builder()
                            .boardId(resultSet.getInt("b.board_id"))
                            .category(Category.valueOf(resultSet.getString("category")))
                            .title(resultSet.getString("b.title"))
                            .name(resultSet.getString("b.name"))
                            .content(resultSet.getString("b.content"))
                            .view(resultSet.getInt("b.view"))
                            .createdAt(resultSet.getTimestamp("b.created_at").toLocalDateTime())
                            .modifiedAt(resultSet.getTimestamp("b.modified_at").toLocalDateTime())
                            .build()
                );
            }
            return boardDto;

        } catch (SQLException e) {
            logger.error("sql: {}", getBoardSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void addBoardViewByBoardId(int boardId) {
        String addViewSql =
                "UPDATE board " +
                "SET view = (view+1) " +
                "WHERE board_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(addViewSql);
        ) {
            preparedStatement.setInt(1, boardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("sql: {}", addViewSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void updateBoard(BoardModifyDto boardModifyDto) {
        String updateBoardSql =
                "UPDATE board " +
                "SET " +
                        "name = ?, " +
                        "password = ?, " +
                        "title = ?, " +
                        "content = ? ," +
                        "modified_at = ? " +
                "WHERE board_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateBoardSql);
                ) {
            preparedStatement.setString(1, boardModifyDto.getName());
            preparedStatement.setString(2, boardModifyDto.getPassword());
            preparedStatement.setString(3, boardModifyDto.getTitle());
            preparedStatement.setString(4, boardModifyDto.getContent());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(6, boardModifyDto.getBoard_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("sql: {}", updateBoardSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void deleteByBoardId(int boardId) {
        String deleteSql =
                "DELETE " +
                "FROM board " +
                "WHERE board_id = ?";

        try(
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                ) {
            preparedStatement.setInt(1, boardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("sql: {}", deleteSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

}

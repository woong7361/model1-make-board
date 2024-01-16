package com.study.board;


import com.study.connection.ConnectionPool;
import com.study.encryption.CipherEncrypt;
import com.study.encryption.EncryptManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcBoardDao implements BoardDao{
    public static final String FIND_ALL = "all";

    @Override
    public void saveBoard(BoardCreateDto boardCreateDto) throws Exception{
        Connection connection = ConnectionPool.getConnection();
        String createBoardSql = "INSERT INTO board (" +
                "category, name, password, title, " +
                "content, view, created_at, modified_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        EncryptManager encryptManger = new CipherEncrypt();
        String password = encryptManger.encrypt(boardCreateDto.getPassword());

        PreparedStatement preparedStatement = connection.prepareStatement(createBoardSql);
        preparedStatement.setString(1, boardCreateDto.getCategory());
        preparedStatement.setString(2, boardCreateDto.getName());
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, boardCreateDto.getTitle());
        preparedStatement.setString(5, boardCreateDto.getContent());
        preparedStatement.setInt(6, 0);
        preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    @Override
    public int getCountBySearchKeyAndCategoryAndDate(String searchKey, String searchCategory, String searchStartDate, String searchEndDate) throws Exception{
        Connection connection = ConnectionPool.getConnection();
        String getCountSql = "SELECT COUNT(*) FROM board WHERE (created_at BETWEEN ? AND ?)" ;

        String categorySearchSql = "";
        if (!FIND_ALL.equals(searchCategory)){
            categorySearchSql = " AND (category = ?)";
        }
        String searchKeySql = "";
        if (!FIND_ALL.equals(searchKey)) {
            searchKeySql = " AND (title LIKE ? OR name LIKE ? OR content LIKE ?)";
        }
        getCountSql = getCountSql + categorySearchSql + searchKeySql;

        LocalDateTime searchStartTimestamp = LocalDateTime.of(LocalDate.parse(searchStartDate), LocalTime.MIN);
        LocalDateTime searchEndTimestamp = LocalDateTime.of(LocalDate.parse(searchEndDate), LocalTime.MIN);


        int index = 1;
        PreparedStatement preparedStatement = connection.prepareStatement(getCountSql);
        preparedStatement.setTimestamp(index++, Timestamp.valueOf(searchStartTimestamp));
        preparedStatement.setTimestamp(index++, Timestamp.valueOf(searchEndTimestamp));

        if (!FIND_ALL.equals(searchCategory)) preparedStatement.setString(index++, searchCategory);
        if (!FIND_ALL.equals(searchKey)){
            preparedStatement.setString(index++, "%"+searchKey+"%");
            preparedStatement.setString(index++, "%"+searchKey+"%");
            preparedStatement.setString(index++, "%"+searchKey+"%");
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        int count = 0;
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return count;
    }

    @Override
    public List<BoardListDto> getBoardListBySearchKeyAndCategoryAndDate(String searchKey, String searchCategory, String searchStartDate, String searchEndDate, Integer currentPage, int pageOffset) throws Exception {
        Connection connection = ConnectionPool.getConnection();
//        String getCountSql = "SELECT (category, title, name, view, created_at, modified_at) FROM board WHERE (created_at BETWEEN ? AND ?)" ;
        String getCountSql = "SELECT * FROM board WHERE (created_at BETWEEN ? AND ?)" ;

        String categorySearchSql = "";
        if (!FIND_ALL.equals(searchCategory)){
            categorySearchSql = " AND (category = ?)";
        }
        String searchKeySql = "";
        if (!FIND_ALL.equals(searchKey)) {
            searchKeySql = " AND (title LIKE ? OR name LIKE ? OR content LIKE ?)";
        }
        String pageSql = " ORDER BY board_id LIMIT ?, ?";
        getCountSql = getCountSql + categorySearchSql + searchKeySql + pageSql;

        LocalDateTime searchStartTimestamp = LocalDateTime.of(LocalDate.parse(searchStartDate), LocalTime.MIN);
        LocalDateTime searchEndTimestamp = LocalDateTime.of(LocalDate.parse(searchEndDate), LocalTime.MIN);


        int index = 1;
        PreparedStatement preparedStatement = connection.prepareStatement(getCountSql);
        preparedStatement.setTimestamp(index++, Timestamp.valueOf(searchStartTimestamp));
        preparedStatement.setTimestamp(index++, Timestamp.valueOf(searchEndTimestamp));

        if (!FIND_ALL.equals(searchCategory)) preparedStatement.setString(index++, searchCategory);
        if (!FIND_ALL.equals(searchKey)){
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
                    resultSet.getString("board_id"),
                    Category.valueOf(resultSet.getString("category")),
                    resultSet.getString("title"),
                    resultSet.getString("name"),
                    resultSet.getInt("view"),
                    resultSet.getTimestamp("created_at").toLocalDateTime(),
                    resultSet.getTimestamp("modified_at").toLocalDateTime(),
                    false
            );
            boardList.add(board);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return boardList;
    }

}

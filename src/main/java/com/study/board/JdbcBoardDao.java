package com.study.board;


import com.study.board.dto.BoardCreateDto;
import com.study.board.dto.BoardDto;
import com.study.board.dto.BoardListDto;
import com.study.comment.dto.CommentDto;
import com.study.connection.ConnectionPool;
import com.study.encryption.CipherEncrypt;
import com.study.encryption.EncryptManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcBoardDao implements BoardDao{
    public static final String FIND_ALL = "all";

    @Override
    public int saveBoard(BoardCreateDto boardCreateDto) throws Exception{
        Connection connection = ConnectionPool.getConnection();
        String createBoardSql = "INSERT INTO board (" +
                "category, name, password, title, " +
                "content, view, created_at, modified_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        EncryptManager encryptManger = new CipherEncrypt();
        String password = encryptManger.encrypt(boardCreateDto.getPassword());

        PreparedStatement preparedStatement = connection.prepareStatement(createBoardSql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, boardCreateDto.getCategory());
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

        preparedStatement.close();
        connection.close();
        return boardId;
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
        String getBoardListSql = "SELECT b.*, (SELECT (count(*) > 0) from file as f where f.board_id = b.board_id) AS count " +
                "FROM board AS b WHERE (created_at BETWEEN ? AND ?)" ;

        String categorySearchSql = "";
        if (!FIND_ALL.equals(searchCategory)){
            categorySearchSql = " AND (category = ?)";
        }
        String searchKeySql = "";
        if (!FIND_ALL.equals(searchKey)) {
            searchKeySql = " AND (title LIKE ? OR name LIKE ? OR content LIKE ?)";
        }
        String pageSql = " ORDER BY board_id LIMIT ?, ?";
        getBoardListSql = getBoardListSql + categorySearchSql + searchKeySql + pageSql;

        LocalDateTime searchStartTimestamp = LocalDateTime.of(LocalDate.parse(searchStartDate), LocalTime.MIN);
        LocalDateTime searchEndTimestamp = LocalDateTime.of(LocalDate.parse(searchEndDate), LocalTime.MIN);


        int index = 1;
        PreparedStatement preparedStatement = connection.prepareStatement(getBoardListSql);
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
                    resultSet.getInt("board_id"),
                    Category.valueOf(resultSet.getString("category")),
                    resultSet.getString("title"),
                    resultSet.getString("name"),
                    resultSet.getInt("view"),
                    resultSet.getTimestamp("created_at").toLocalDateTime(),
                    resultSet.getTimestamp("modified_at").toLocalDateTime(),
                    resultSet.getBoolean("count")
            );
            boardList.add(board);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return boardList;
    }

    @Override
    public Optional<BoardDto> getBoardByBoardId(int boardId) throws Exception{
        Connection connection = ConnectionPool.getConnection();
        String getBoardSql = "SELECT * FROM board AS b LEFT JOIN comment AS c ON b.board_id = c.board_id WHERE (b.board_id = ?)" ;


        PreparedStatement preparedStatement = connection.prepareStatement(getBoardSql);
        preparedStatement.setInt(1, boardId);

        ResultSet resultSet = preparedStatement.executeQuery();

        Optional<BoardDto> boardDto = Optional.empty();
        ArrayList<CommentDto> commentList = new ArrayList<>();

        boolean flag = true;
        while (resultSet.next()) {
            if (flag) {
                boardDto = Optional.of(new BoardDto(
                        resultSet.getInt("b.board_id"),
                        Category.valueOf(resultSet.getString("b.category")),
                        resultSet.getString("b.title"),
                        resultSet.getString("b.name"),
                        resultSet.getString("b.content"),
                        resultSet.getInt("b.view"),
                        resultSet.getTimestamp("b.created_at").toLocalDateTime(),
                        resultSet.getTimestamp("b.modified_at").toLocalDateTime(),
                        commentList
                ));
                flag = false;
            }

            if (Optional.ofNullable(resultSet.getTimestamp("c.created_at")).isPresent()) {
                commentList.add(new CommentDto(
                        resultSet.getInt("c.comment_id"),
                        resultSet.getString("c.content"),
                        resultSet.getTimestamp("c.created_at").toLocalDateTime()
                ));
            }
        }


        resultSet.close();
        preparedStatement.close();
        connection.close();


        return boardDto;
    }

}

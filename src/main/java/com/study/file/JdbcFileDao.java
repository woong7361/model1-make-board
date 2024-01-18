package com.study.file;

import com.study.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcFileDao implements FileDao{

    @Override
    public void saveFileList(List<FileCreateDto> fileList, int boardId) throws Exception{
        Connection connection = ConnectionPool.getConnection();
        String createFileSql = "INSERT INTO file (" +
                "original_name, name, path, extension, board_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        for (FileCreateDto fileCreateDto : fileList) {
            PreparedStatement preparedStatement = connection.prepareStatement(createFileSql);
            preparedStatement.setString(1, fileCreateDto.getOriginalFileName());
            preparedStatement.setString(2, fileCreateDto.getFileName());
            preparedStatement.setString(3, fileCreateDto.getFilePath());
            preparedStatement.setString(4, fileCreateDto.getExtension());
            preparedStatement.setInt(5, boardId);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        connection.close();

    }

    @Override
    public List<FileDto> getFileByBoardId(int boardId) throws Exception {
        Connection connection = ConnectionPool.getConnection();
        String getFileSql = "SELECT * FROM file AS f WHERE (f.board_id = ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(getFileSql);
        preparedStatement.setInt(1, boardId);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<FileDto> fileDtoList = new ArrayList<>();
        while (resultSet.next()) {
            fileDtoList.add(new FileDto(
                    resultSet.getString("original_name"),
                    resultSet.getString("name"),
                    resultSet.getString("path")
            ));
        }

        preparedStatement.close();
        connection.close();

        return fileDtoList;
    }
}

package com.study.file;

import com.study.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                    resultSet.getInt("file_id"),
                    resultSet.getString("original_name")
            ));
        }

        preparedStatement.close();
        connection.close();

        return fileDtoList;
    }

    @Override
    public Optional<FileDownloadDto> getFileByFileId(int fileId) throws Exception {
        Connection connection = ConnectionPool.getConnection();
        String getFileSql = "SELECT * FROM file WHERE (file_id = ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(getFileSql);
        preparedStatement.setInt(1, fileId);
        ResultSet resultSet = preparedStatement.executeQuery();

        Optional<FileDownloadDto> fileDto = Optional.empty();
        if (resultSet.next()) {
            fileDto = Optional.of(new FileDownloadDto(
                    resultSet.getString("original_name"),
                    resultSet.getString("name"),
                    resultSet.getString("path")
            ));
        }

        preparedStatement.close();
        connection.close();

        return fileDto;

    }

    @Override
    public void deleteFileList(List<Integer> deleteFileIdList) throws Exception {
        Connection connection = ConnectionPool.getConnection();

        for (Integer fileId : deleteFileIdList) {
            String deleteFileSql = "DELETE FROM file WHERE (file_id = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteFileSql);
            preparedStatement.setInt(1, fileId);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }

        connection.close();
    }

    @Override
    public void deleteByBoardId(int boardId) throws Exception {
        Connection connection = ConnectionPool.getConnection();
        String deleteSql = "DELETE FROM file WHERE board_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
        preparedStatement.setInt(1, boardId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    @Override
    public List<String> getFilePathListByBoardId(int boardId) throws Exception {
        Connection connection = ConnectionPool.getConnection();
        String deleteSql = "SELECT path FROM file WHERE board_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
        preparedStatement.setInt(1, boardId);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> filePathList = new ArrayList<>();
        while (resultSet.next()) {
            filePathList.add(resultSet.getString("path"));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return filePathList;
    }
}

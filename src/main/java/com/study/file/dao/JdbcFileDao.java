package com.study.file.dao;

import com.study.connection.ConnectionPool;
import com.study.exception.WrapCheckedException;
import com.study.file.dto.FileCreateDto;
import com.study.file.dto.FileDownloadDto;
import com.study.file.dto.FileDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcFileDao implements FileDao{

    @Override
    public void saveFileListIdList(List<FileCreateDto> fileList, int boardId) {
        String createFileSql = "INSERT INTO file (" +
                "original_name, name, path, extension, board_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(createFileSql);

        ) {
            for (FileCreateDto fileCreateDto : fileList) {
                preparedStatement.setString(1, fileCreateDto.getOriginalFileName());
                preparedStatement.setString(2, fileCreateDto.getFileName());
                preparedStatement.setString(3, fileCreateDto.getFilePath());
                preparedStatement.setString(4, fileCreateDto.getExtension());
                preparedStatement.setInt(5, boardId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new WrapCheckedException("sql exception", e);
        }
    }

    @Override
    public List<FileDto> getFileByBoardId(int boardId) {
        String getFileSql = "SELECT * FROM file AS f WHERE (f.board_id = ?)";

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getFileSql);
        ) {
            preparedStatement.setInt(1, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<FileDto> fileDtoList = new ArrayList<>();
            while (resultSet.next()) {
                fileDtoList.add(new FileDto(
                        resultSet.getInt("file_id"),
                        resultSet.getString("original_name")
                ));
            }

            return fileDtoList;
        } catch (SQLException e) {
            throw new WrapCheckedException("sql Exception", e);
        }
    }

    @Override
    public Optional<FileDownloadDto> getFileByFileId(int fileId) throws SQLException {
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
    public void deleteFileByListIdList(List<Integer> deleteFileIdList) {
        String deleteFileSql = "DELETE FROM file WHERE (file_id = ?)";

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteFileSql);
                ) {
            for (Integer fileId : deleteFileIdList) {
                preparedStatement.setInt(1, fileId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new WrapCheckedException("sql Exception", e);
        }
    }

    @Override
    public void deleteByBoardId(int boardId) {
        String deleteSql = "DELETE FROM file WHERE board_id = ?";

        try(
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                ){
            preparedStatement.setInt(1, boardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new WrapCheckedException("sql Exception", e);
        }
    }

    @Override
    public List<String> getFilePathListByBoardId(int boardId) {
        String deleteSql = "SELECT path FROM file WHERE board_id = ?";

        try(
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                ) {
            preparedStatement.setInt(1, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> filePathList = new ArrayList<>();
            while (resultSet.next()) {
                filePathList.add(resultSet.getString("path"));
            }

            resultSet.close();

            return filePathList;
        } catch (SQLException e) {
            throw new WrapCheckedException("sql Exception", e);
        }
    }

    @Override
    public List<String> getFilePathListByIdList(List<Integer> FileIdList) {
        String deleteFileSql = "SELECT path FROM file WHERE (file_id = ?)";

        try (
                Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteFileSql);
                ) {

            List<String> filePathList = new ArrayList<>();
            for (Integer fileId : FileIdList) {
                preparedStatement.setInt(1, fileId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    filePathList.add(resultSet.getString("path"));
                }
            }

            return filePathList;
        } catch (SQLException e) {
            throw new WrapCheckedException("sql Exception", e);
        }

    }
}

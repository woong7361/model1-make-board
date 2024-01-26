package com.study.file.dao;

import com.study.connection.DBConnection;
import com.study.exception.WrapCheckedException;
import com.study.file.dto.FileCreateDto;
import com.study.file.dto.FileDownloadDto;
import com.study.file.dto.FileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.study.constant.ExceptionConstant.SQL_EXCEPTION_MESSAGE;

public class JdbcFileDao implements FileDao{
    private final Logger logger = LoggerFactory.getLogger(JdbcFileDao.class);

    @Override
    public void saveFileList(List<FileCreateDto> fileList, int boardId) {
        String createFileSql = "INSERT INTO file (" +
                "original_name, name, path, extension, board_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
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
            logger.error("sql: {}", createFileSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<FileDto> getFileDtoByBoardId(int boardId) {
        String getFileSql = "SELECT * FROM file AS f WHERE (f.board_id = ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getFileSql);
        ) {
            preparedStatement.setInt(1, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<FileDto> fileDtoList = new ArrayList<>();
            while (resultSet.next()) {
                FileDto fileDto = FileDto.builder()
                        .fileId(resultSet.getInt("file_id"))
                        .originalName(resultSet.getString("original_name"))
                        .build();

                fileDtoList.add(fileDto);
            }

            return fileDtoList;
        } catch (SQLException e) {
            logger.error("sql: {}", getFileSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<FileDownloadDto> getFileDownloadDtoByFileId(int fileId) {
        String getFileSql = "SELECT * FROM file WHERE (file_id = ?)";

        try(
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getFileSql);
                ) {
            preparedStatement.setInt(1, fileId);
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<FileDownloadDto> fileDto = Optional.empty();
            if (resultSet.next()) {
                fileDto = Optional.of(FileDownloadDto.builder()
                        .originalName(resultSet.getString("original_name"))
                        .name(resultSet.getString("name"))
                        .path(resultSet.getString("path"))
                        .build());
            }

            return fileDto;
        }catch (SQLException e) {
            logger.error("sql: {}", getFileSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void deleteFileByListIdList(List<Integer> deleteFileIdList) {
        String deleteFileSql = "DELETE FROM file WHERE (file_id = ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteFileSql);
                ) {
            for (Integer fileId : deleteFileIdList) {
                preparedStatement.setInt(1, fileId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("sql: {}", deleteFileSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void deleteByBoardId(int boardId) {
        String deleteSql = "DELETE FROM file WHERE board_id = ?";

        try(
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                ){
            preparedStatement.setInt(1, boardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("sql: {}", deleteSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<String> getFilePathListByBoardId(int boardId) {
        String filePathSql = "SELECT path, name FROM file WHERE board_id = ?";

        try(
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(filePathSql);
                ) {
            preparedStatement.setInt(1, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> filePathList = new ArrayList<>();
            while (resultSet.next()) {
                String fullPath = resultSet.getString("path") + "/" + resultSet.getString("name");
                filePathList.add(fullPath);
            }

            resultSet.close();

            return filePathList;
        } catch (SQLException e) {
            logger.error("sql: {}", filePathSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<String> getFileFullPathListByIdList(List<Integer> FileIdList) {
        String fullPathSql = "SELECT path, name FROM file WHERE (file_id = ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(fullPathSql);
                ) {

            List<String> filePathList = new ArrayList<>();
            for (Integer fileId : FileIdList) {
                preparedStatement.setInt(1, fileId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String fullPath = resultSet.getString("path") + "/" + resultSet.getString("name");
                    filePathList.add(fullPath);
                }
            }

            return filePathList;
        } catch (SQLException e) {
            logger.error("sql: {}", fullPathSql, e);
            throw new WrapCheckedException(SQL_EXCEPTION_MESSAGE, e);
        }

    }
}

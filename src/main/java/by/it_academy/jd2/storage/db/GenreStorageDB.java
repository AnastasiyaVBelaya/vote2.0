package by.it_academy.jd2.storage.db;

import by.it_academy.jd2.dto.genre.GenreResultDTO;
import by.it_academy.jd2.dto.genre.GenreSaveDTO;
import by.it_academy.jd2.enums.EStatus;
import by.it_academy.jd2.storage.api.IGenreStorage;
import by.it_academy.jd2.storage.db.utils.DBUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GenreStorageDB implements IGenreStorage {

    private static final String INSERT_QUERY =
            "INSERT INTO app.genre(name, description, status, dateCreated, lastModified) " +
                    "VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) RETURNING id;";

    private static final String SELECT_BY_ID_QUERY =
            "SELECT id, name, description, status, dateCreated, lastModified " +
                    "FROM app.genre WHERE id = ?;";

    private static final String SELECT_ALL_QUERY =
            "SELECT id, name, description, status, dateCreated, lastModified " +
                    "FROM app.genre;";

    private static final String UPDATE_QUERY =
            "UPDATE app.genre SET " +
                    "name = ?, description = ?, lastModified = CURRENT_TIMESTAMP " +
                    "WHERE id = ?;";

    private static final String DELETE_QUERY =
            "UPDATE app.genre SET " +
                    "status = ?, lastModified = CURRENT_TIMESTAMP " +
                    "WHERE id = ?;";

    @Override
    public Long create(GenreSaveDTO genreSaveDTO) {
        try (Connection connect = DBUtils.getConnect();
             PreparedStatement statement = connect.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, genreSaveDTO.getName());
            statement.setString(2, genreSaveDTO.getDescription());
            statement.setString(3, EStatus.ACTIVE.name());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании жанра", e);
        }
        return null;
    }

    @Override
    public GenreResultDTO get(Long id) {
        try (Connection connect = DBUtils.getConnect();
             PreparedStatement statement = connect.prepareStatement(SELECT_BY_ID_QUERY)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    EStatus status = EStatus.valueOf(resultSet.getString("status"));
                    Timestamp dateCreated = resultSet.getTimestamp("dateCreated");
                    Timestamp lastModified = resultSet.getTimestamp("lastModified");
                    return new GenreResultDTO(id, name, description, status.name(),
                            dateCreated.toLocalDateTime(), lastModified.toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении жанра", e);
        }
        return null;
    }

    @Override
    public Map<Long, GenreResultDTO> get() {
        try (Connection connect = DBUtils.getConnect();
             Statement statement = connect.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY)) {

            Map<Long, GenreResultDTO> result = new HashMap<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                EStatus status = EStatus.valueOf(resultSet.getString("status"));
                Timestamp dateCreated = resultSet.getTimestamp("dateCreated");
                Timestamp lastModified = resultSet.getTimestamp("lastModified");
                GenreResultDTO genre = new GenreResultDTO(id, name, description, status.name(),
                        dateCreated.toLocalDateTime(), lastModified.toLocalDateTime());
                result.put(id, genre);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении жанров", e);
        }
    }

    @Override
    public boolean update(Long id, GenreSaveDTO genreSaveDTO) {
        try (Connection connect = DBUtils.getConnect();
             PreparedStatement statement = connect.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, genreSaveDTO.getName());
            statement.setString(2, genreSaveDTO.getDescription());
            statement.setLong(3, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении жанра", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connect = DBUtils.getConnect();
             PreparedStatement statement = connect.prepareStatement(DELETE_QUERY)) {

            statement.setString(1, EStatus.DELETED.name());
            statement.setLong(2, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении жанра", e);
        }
    }
}

package by.it_academy.jd2.storage.db;

import by.it_academy.jd2.dto.artist.ArtistResultDTO;
import by.it_academy.jd2.dto.artist.ArtistSaveDTO;
import by.it_academy.jd2.enums.EStatus;
import by.it_academy.jd2.storage.api.IArtistStorage;
import by.it_academy.jd2.storage.db.utils.DBUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ArtistStorageDB implements IArtistStorage {

    private static final String INSERT_QUERY =
            "INSERT INTO app.artist (" +
            "name, genre, biography, status, dateCreated, lastModified) " +
            "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) RETURNING id;";

    private static final String SELECT_BY_ID_QUERY =
            "SELECT id, name, genre, biography, status, dateCreated, lastModified " +
            "FROM app.artist " +
            "WHERE id = ?;";

    private static final String SELECT_ALL_QUERY =
            "SELECT id, name, genre, biography, status, dateCreated, lastModified " +
            "FROM app.artist;";

    private static final String UPDATE_QUERY =
            "UPDATE app.artist SET " +
                    "name = ?, genre = ?, biography = ?, lastModified = CURRENT_TIMESTAMP " +
                    "WHERE id = ?;";

    private static final String DELETE_QUERY =
            "UPDATE app.artist SET " +
            "status = ?, lastModified = CURRENT_TIMESTAMP " +
            "WHERE id = ?;";

    @Override
    public Long create(ArtistSaveDTO artistSaveDTO) {
        try (Connection connect = DBUtils.getConnect();
             PreparedStatement statement = connect.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, artistSaveDTO.getName());
            statement.setString(2, artistSaveDTO.getGenre());
            statement.setString(3, artistSaveDTO.getBiography());
            statement.setString(4, EStatus.ACTIVE.name());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании исполнителя", e);
        }
        return null;
    }

    @Override
    public ArtistResultDTO get(Long id) {
        try (Connection connect = DBUtils.getConnect();
             PreparedStatement statement = connect.prepareStatement(SELECT_BY_ID_QUERY)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String genre = resultSet.getString("genre");
                    String biography = resultSet.getString("biography");
                    EStatus status = EStatus.valueOf(resultSet.getString("status"));
                    LocalDateTime dateCreated = resultSet.getTimestamp("dateCreated").toLocalDateTime();
                    LocalDateTime lastModified = resultSet.getTimestamp("lastModified").toLocalDateTime();
                    return new ArtistResultDTO(id, name, genre, biography, status.name(), dateCreated, lastModified);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении исполнителя", e);
        }
        return null;
    }

    @Override
    public Map<Long, ArtistResultDTO> get() {
        try (Connection connect = DBUtils.getConnect();
             Statement statement = connect.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY)) {

            Map<Long, ArtistResultDTO> result = new HashMap<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String genre = resultSet.getString("genre");
                String biography = resultSet.getString("biography");
                EStatus status = EStatus.valueOf(resultSet.getString("status"));
                LocalDateTime dateCreated = resultSet.getTimestamp("dateCreated").toLocalDateTime();
                LocalDateTime lastModified = resultSet.getTimestamp("lastModified").toLocalDateTime();
                ArtistResultDTO artist = new ArtistResultDTO(id, name, genre, biography, status.name(),
                        dateCreated, lastModified);
                result.put(id, artist);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении исполнителей", e);
        }
    }

    @Override
    public boolean update(Long id, ArtistSaveDTO artistSaveDTO) {
        try (Connection connect = DBUtils.getConnect();
             PreparedStatement statement = connect.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, artistSaveDTO.getName());
            statement.setString(2, artistSaveDTO.getGenre());
            statement.setString(3, artistSaveDTO.getBiography());
            statement.setLong(4, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении исполнителя", e);
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
            throw new RuntimeException("Ошибка при удалении исполнителя", e);
        }
    }
}

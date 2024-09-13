package by.it_academy.jd2.storage.db;

import by.it_academy.jd2.dto.vote.VoteSaveDTO;
import by.it_academy.jd2.dto.vote.VoteResultDTO;
import by.it_academy.jd2.storage.api.IVoteStorage;
import by.it_academy.jd2.storage.db.utils.DBUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class VoteStorageDB implements IVoteStorage {

    private static final String INSERT_VOTE_QUERY =
            "INSERT INTO app.vote (artist_id, about, dateCreated) VALUES (?, ?, ?) RETURNING id";
    private static final String INSERT_CROSS_VOTE_GENRE_QUERY =
            "INSERT INTO app.cross_vote_genre (vote_id, genre_id) VALUES (?, ?)";

    private static final String SELECT_ARTIST_VOTES_QUERY = "SELECT a.id, COUNT(v.id) AS vote_count " +
            "FROM app.artist a LEFT JOIN app.vote v ON a.id = v.artist_id " +
            "GROUP BY a.id";

    private static final String SELECT_GENRE_VOTES_QUERY = "SELECT g.id, COUNT(cv.genre_id) AS vote_count " +
            "FROM app.genre g LEFT JOIN app.cross_vote_genre cv ON g.id = cv.genre_id " +
            "GROUP BY g.id";

    private static final String SELECT_VOTES_QUERY = "SELECT v.id AS vote_id, v.artist_id, v.about, v.dateCreated, " +
            "array_agg(cv.genre_id) AS genre_ids " +
            "FROM app.vote v LEFT JOIN app.cross_vote_genre cv ON v.id = cv.vote_id " +
            "GROUP BY v.id, v.artist_id, v.about, v.dateCreated";

    @Override
    public Long addVote(VoteSaveDTO voteSaveDTO) {
        try (Connection connection = DBUtils.getConnect();
             PreparedStatement voteStatement =
                     connection.prepareStatement(INSERT_VOTE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            voteStatement.setLong(1, voteSaveDTO.getArtistId());
            voteStatement.setString(2, voteSaveDTO.getAbout());
            voteStatement.setObject(3, LocalDateTime.now());
            int affectedRows = voteStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet voteResultSet = voteStatement.getGeneratedKeys()) {
                    if (voteResultSet.next()) {
                        long voteId = voteResultSet.getLong(1);

                        try (PreparedStatement crossVoteGenreStatement =
                                     connection.prepareStatement(INSERT_CROSS_VOTE_GENRE_QUERY)) {
                            for (Long genreId : voteSaveDTO.getGenreIds()) {
                                crossVoteGenreStatement.setLong(1, voteId);
                                crossVoteGenreStatement.setLong(2, genreId);
                                crossVoteGenreStatement.addBatch();
                            }
                            crossVoteGenreStatement.executeBatch();
                        }
                        return voteId;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось добавить голос"+e);
        }
        return null;
    }

    @Override
    public Map<Long, Integer> getArtistVotes() {
        Map<Long, Integer> artistVotes = new HashMap<>();
        try (Connection connection = DBUtils.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ARTIST_VOTES_QUERY)) {

            while (resultSet.next()) {
                artistVotes.put(resultSet.getLong("id"),
                        resultSet.getInt("vote_count"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить информацию об исполнителях"+e);
        }
        return artistVotes;
    }

    @Override
    public Map<Long, Integer> getGenreVotes() {
        Map<Long, Integer> genreVotes = new HashMap<>();
        try (Connection connection = DBUtils.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_GENRE_VOTES_QUERY)) {

            while (resultSet.next()) {
                genreVotes.put(resultSet.getLong("id"), resultSet.getInt("vote_count"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить информацию о жанрах"+e);
        }
        return genreVotes;
    }

    @Override
    public List<VoteResultDTO> getVotes() {
        List<VoteResultDTO> votes = new ArrayList<>();
        try (Connection connection = DBUtils.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_VOTES_QUERY)) {

            while (resultSet.next()) {
                Long artistId = resultSet.getLong("artist_id");

                Array array = resultSet.getArray("genre_ids");
                Long[] genreIdsArray = (Long[]) array.getArray();
                List<Long> genreIds = genreIdsArray != null ? Arrays.asList(genreIdsArray) : new ArrayList<>();

                String about = resultSet.getString("about");

                LocalDateTime dateCreated = resultSet.getTimestamp("dateCreated").toLocalDateTime();

                votes.add(new VoteResultDTO(artistId, genreIds, about, dateCreated));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить информацию о голосах"+e);
        }
        return votes;
    }
}

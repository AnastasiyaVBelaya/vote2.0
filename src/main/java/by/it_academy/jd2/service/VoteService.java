package by.it_academy.jd2.service;

import by.it_academy.jd2.dto.artist.ArtistResultDTO;
import by.it_academy.jd2.dto.genre.GenreResultDTO;
import by.it_academy.jd2.dto.vote.VoteSaveDTO;
import by.it_academy.jd2.dto.vote.VoteResultDTO;
import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.service.api.IVoteService;
import by.it_academy.jd2.storage.api.IVoteStorage;
import by.it_academy.jd2.service.factory.ArtistServiceFactory;
import by.it_academy.jd2.service.factory.GenreServiceFactory;
import by.it_academy.jd2.storage.db.factory.VoteStorageDBFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class VoteService implements IVoteService {

    private final IVoteStorage voteStorage;
    private final IArtistService artistService;
    private final IGenreService genreService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private final Map<String, LocalDateTime> voteTimestamps = new HashMap<>();

    public VoteService(IVoteStorage voteStorage, IArtistService artistService, IGenreService genreService) {
        this.voteStorage = voteStorage;
        this.artistService = artistService;
        this.genreService = genreService;
    }

    @Override
    public void create(VoteSaveDTO voteSaveDTO) {
        validateVote(voteSaveDTO);
        LocalDateTime voteDateTime = LocalDateTime.now();
        Long voteId = voteStorage.addVote(voteSaveDTO);
    }

    private void validateVote(VoteSaveDTO voteSaveDTO) {
        boolean artistExists = artistService.get().values().stream()
                .anyMatch(artist -> artist.getId().equals(voteSaveDTO.getArtistId()));
        if (!artistExists) {
            throw new IllegalArgumentException("Исполнитель не существует");
        }

        List<Long> genreIds = voteSaveDTO.getGenreIds();
        if (genreIds == null || genreIds.size() < 3) {
            throw new IllegalArgumentException("Необходимо указать не менее 3 жанров");
        }

        if (genreIds.contains(null)) {
            throw new IllegalArgumentException("Жанр не выбран");
        }

        if (voteSaveDTO.getAbout() == null || voteSaveDTO.getAbout().isBlank()) {
            throw new IllegalArgumentException("Поле не заполнено");
        }
    }


    @Override
    public Map<String, Object> getResults() {
        Map<String, Object> results = new HashMap<>();


        List<ArtistResultDTO> artistList = new ArrayList<>(artistService.get().values());
        List<GenreResultDTO> genreList = new ArrayList<>(genreService.get().values());

        Map<Long, String> artistNameMap = artistList.stream()
                .collect(Collectors.toMap(ArtistResultDTO::getId, ArtistResultDTO::getName));
        Map<Long, String> genreNameMap = genreList.stream()
                .collect(Collectors.toMap(GenreResultDTO::getId, GenreResultDTO::getName));


        Map<Long, Integer> artistVotes = voteStorage.getArtistVotes();
        Map<Long, Integer> genreVotes = voteStorage.getGenreVotes();

        Map<String, Integer> finalArtistVotes = initializeAndSortResults(artistVotes, artistNameMap);
        Map<String, Integer> finalGenreVotes = initializeAndSortResults(genreVotes, genreNameMap);

        results.put("artists", finalArtistVotes);
        results.put("genres", finalGenreVotes);
        results.put("aboutVotes", formatVoteResults());

        return results;
    }

    private Map<String, Integer> initializeAndSortResults(Map<Long, Integer> actualVotes, Map<Long, String> nameMap) {
        return nameMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getValue,
                        entry -> actualVotes.getOrDefault(entry.getKey(), 0),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private List<String> formatVoteResults() {
        return voteStorage.getVotes().stream()
                .sorted(Comparator.comparing(VoteResultDTO::getDateCreated).reversed())
                .map(vote -> String.format("%s - %s", vote.getAbout(), vote.getDateCreated().format(FORMATTER)))
                .collect(Collectors.toList());
    }

    public static VoteService getInstance() {
        return new VoteService(
                VoteStorageDBFactory.getInstance(),
                ArtistServiceFactory.getInstance(),
                GenreServiceFactory.getInstance()
        );
    }
}

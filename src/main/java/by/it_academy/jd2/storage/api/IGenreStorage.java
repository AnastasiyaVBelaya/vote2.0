package by.it_academy.jd2.storage.api;

import by.it_academy.jd2.dto.genre.GenreResultDTO;
import by.it_academy.jd2.dto.genre.GenreSaveDTO;

import java.util.Map;

public interface IGenreStorage {
    Long create(GenreSaveDTO genreSaveDTO);
    GenreResultDTO get(Long id);
    Map<Long, GenreResultDTO> get();
    boolean update(Long id, GenreSaveDTO genreSaveDTO);
    boolean delete(Long id);
}

package by.it_academy.jd2.service;

import by.it_academy.jd2.dto.genre.GenreResultDTO;
import by.it_academy.jd2.dto.genre.GenreSaveDTO;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.storage.api.IGenreStorage;

import java.util.Map;

public class GenreService implements IGenreService {

    private final IGenreStorage storage;

    public GenreService(IGenreStorage storage) {
        if (storage == null) {
            throw new IllegalArgumentException("Хранилище не может быть null");
        }
        this.storage = storage;
    }

    @Override
    public Long create(GenreSaveDTO genreSaveDTO) {
        if (genreSaveDTO == null) {
            throw new IllegalArgumentException("Объект для сохранения жанра не может быть null");
        }
        if (genreSaveDTO.getName() == null || genreSaveDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Название жанра не может быть пустым");
        }
        return storage.create(genreSaveDTO);
    }

    @Override
    public GenreResultDTO get(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Некорректный ID жанра");
        }
        GenreResultDTO genre = storage.get(id);
        if (genre == null) {
            throw new RuntimeException("Жанр не найден");
        }
        return genre;
    }

    @Override
    public Map<Long, GenreResultDTO> get() {
        return storage.get();
    }

    @Override
    public boolean update(Long id, GenreSaveDTO genreSaveDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Некорректный ID жанра");
        }
        if (genreSaveDTO == null) {
            throw new IllegalArgumentException("Объект для обновления жанра не может быть null");
        }
        if (genreSaveDTO.getName() == null || genreSaveDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Название жанра не может быть пустым");
        }
        return storage.update(id, genreSaveDTO);
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Некорректный ID жанра");
        }
        return storage.delete(id);
    }
}

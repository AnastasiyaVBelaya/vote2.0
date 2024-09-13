package by.it_academy.jd2.service;

import by.it_academy.jd2.dto.artist.ArtistResultDTO;
import by.it_academy.jd2.dto.artist.ArtistSaveDTO;
import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.storage.api.IArtistStorage;

import java.util.Map;

public class ArtistService implements IArtistService {

    private final IArtistStorage storage;

    public ArtistService(IArtistStorage storage) {
        this.storage = storage;
    }

    @Override
    public Long create(ArtistSaveDTO artistSaveDTO) {
        if (artistSaveDTO == null) {
            throw new IllegalArgumentException("Объект для сохранения исполнителя не может быть null");
        }
        if (artistSaveDTO.getName() == null || artistSaveDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Имя исполнителя не может быть пустым");
        }
        return storage.create(artistSaveDTO);
    }

    @Override
    public ArtistResultDTO get(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Некорректный ID исполнителя");
        }
        ArtistResultDTO artist = storage.get(id);
        if (artist == null) {
            throw new RuntimeException("Исполнитель не найден");
        }
        return artist;
    }

    @Override
    public Map<Long, ArtistResultDTO> get() {
        return storage.get();
    }

    @Override
    public boolean update(Long id, ArtistSaveDTO artistSaveDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Некорректный ID исполнителя");
        }
        if (artistSaveDTO == null) {
            throw new IllegalArgumentException("Объект для сохранения исполнителя не может быть null");
        }
        if (artistSaveDTO.getName() == null || artistSaveDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Имя исполнителя не может быть пустым");
        }
        return storage.update(id, artistSaveDTO);
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Некорректный ID исполнителя");
        }
        return storage.delete(id);
    }
}

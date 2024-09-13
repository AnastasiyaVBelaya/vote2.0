package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dto.artist.ArtistResultDTO;
import by.it_academy.jd2.dto.artist.ArtistSaveDTO;

import java.util.Map;

public interface IArtistService {
    Long create(ArtistSaveDTO artistSaveDTO);

    ArtistResultDTO get(Long id);

    Map<Long, ArtistResultDTO> get();

    boolean update(Long id, ArtistSaveDTO artistSaveDTO);

    boolean delete(Long id);
}

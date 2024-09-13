package by.it_academy.jd2.service.factory;

import by.it_academy.jd2.service.GenreService;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.storage.api.IGenreStorage;
import by.it_academy.jd2.storage.db.factory.StorageFactory;

public class GenreServiceFactory {

    private static final IGenreService instance;

    static {
        IGenreStorage genreStorage = StorageFactory.getGenreStorage();
        instance = new GenreService(genreStorage);
    }

    private GenreServiceFactory() {
    }

    public static IGenreService getInstance() {
        return instance;
    }
}

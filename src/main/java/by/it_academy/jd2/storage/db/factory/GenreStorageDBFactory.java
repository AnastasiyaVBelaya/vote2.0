package by.it_academy.jd2.storage.db.factory;

import by.it_academy.jd2.storage.api.IGenreStorage;
import by.it_academy.jd2.storage.db.GenreStorageDB;

public class GenreStorageDBFactory {

    private static final IGenreStorage instance;

    static {
        String envConfig = System.getenv("GENRE_STORAGE_CONFIG");

        if (envConfig == null || envConfig.isEmpty() || "GenreStorageDB".equals(envConfig)) {
            instance = new GenreStorageDB();
        } else {
            throw new IllegalArgumentException("Неизвестное хранилище: " + envConfig);
        }
    }

    public static IGenreStorage getInstance() {
        return instance;
    }
}

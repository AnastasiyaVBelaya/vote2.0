package by.it_academy.jd2.storage.db.factory;

import by.it_academy.jd2.storage.api.IArtistStorage;
import by.it_academy.jd2.storage.db.ArtistStorageDB;

public class ArtistStorageDBFactory {

    private static final IArtistStorage instance;

    static {
        String envConfig = System.getenv("ARTIST_STORAGE_CONFIG");

        if (envConfig == null || envConfig.isEmpty() || "ArtistStorageDB".equals(envConfig)) {
            instance = new ArtistStorageDB();
        } else {
            throw new IllegalArgumentException("Неизвестное хранилище: " + envConfig);
        }
    }

    public static IArtistStorage getInstance() {
        return instance;
    }
}
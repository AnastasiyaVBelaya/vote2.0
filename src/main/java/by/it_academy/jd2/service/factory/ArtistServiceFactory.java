package by.it_academy.jd2.service.factory;

import by.it_academy.jd2.service.ArtistService;
import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.storage.api.IArtistStorage;
import by.it_academy.jd2.storage.db.factory.ArtistStorageDBFactory;

public class ArtistServiceFactory {
    private static final IArtistService instance;

    static {
        IArtistStorage artistStorage = ArtistStorageDBFactory.getInstance();
        instance = new ArtistService(artistStorage);
    }

    private ArtistServiceFactory() {
    }

    public static IArtistService getInstance() {
        return instance;
    }
}

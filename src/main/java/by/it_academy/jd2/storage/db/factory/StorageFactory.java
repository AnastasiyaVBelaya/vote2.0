package by.it_academy.jd2.storage.db.factory;

import by.it_academy.jd2.storage.api.IArtistStorage;
import by.it_academy.jd2.storage.api.IGenreStorage;
import by.it_academy.jd2.storage.api.IVoteStorage;

public class StorageFactory {

    private static IArtistStorage artistStorage = ArtistStorageDBFactory.getInstance();
    private static IGenreStorage genreStorage = GenreStorageDBFactory.getInstance();
    private static IVoteStorage voteStorage = VoteStorageDBFactory.getInstance();

    public static IArtistStorage getArtistStorage() {

        return artistStorage;
    }

    public static IGenreStorage getGenreStorage() {
        return genreStorage;
    }

    public static IVoteStorage getVoteStorage() {
        return voteStorage;
    }
}

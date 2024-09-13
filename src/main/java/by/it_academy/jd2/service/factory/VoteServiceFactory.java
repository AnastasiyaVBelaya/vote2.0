package by.it_academy.jd2.service.factory;

import by.it_academy.jd2.service.VoteService;
import by.it_academy.jd2.service.api.IVoteService;
import by.it_academy.jd2.storage.api.IVoteStorage;
import by.it_academy.jd2.storage.db.factory.VoteStorageDBFactory;

public class VoteServiceFactory {
    private final static IVoteService instance = new VoteService(
            VoteStorageDBFactory.getInstance(),
            ArtistServiceFactory.getInstance(),
            GenreServiceFactory.getInstance()
    );
    public static IVoteService getInstance() {
        return instance;
    }
}

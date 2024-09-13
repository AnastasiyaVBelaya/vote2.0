package by.it_academy.jd2.storage.db.factory;

import by.it_academy.jd2.storage.api.IVoteStorage;
import by.it_academy.jd2.storage.db.VoteStorageDB;

public class VoteStorageDBFactory {

    private static final IVoteStorage instance;

    static {
        String envConfig = System.getenv("VOTE_STORAGE_CONFIG");

        if (envConfig == null || envConfig.isEmpty() || "VoteStorageDB".equals(envConfig)) {
            instance = new VoteStorageDB();
        } else {
            throw new IllegalArgumentException("Неизвестное хранилище: " + envConfig);
        }
    }

    public static IVoteStorage getInstance() {
        return instance;
    }
}
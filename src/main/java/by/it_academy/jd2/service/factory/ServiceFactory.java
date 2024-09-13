package by.it_academy.jd2.service.factory;

import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.service.api.IVoteService;

public class ServiceFactory {
    private static IArtistService artistService = ArtistServiceFactory.getInstance();
    private static IGenreService genreService = GenreServiceFactory.getInstance();
    private static IVoteService voteService = VoteServiceFactory.getInstance();

    public static IArtistService getArtistService() {
        return artistService;
    }

    public static IGenreService getGenreService() {
        return genreService;
    }

    public static IVoteService getVoteService() {
        return voteService;
    }
}
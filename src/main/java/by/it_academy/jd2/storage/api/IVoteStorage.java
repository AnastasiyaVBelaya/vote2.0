package by.it_academy.jd2.storage.api;

import by.it_academy.jd2.dto.vote.VoteResultDTO;
import by.it_academy.jd2.dto.vote.VoteSaveDTO;

import java.util.List;
import java.util.Map;

public interface IVoteStorage {
    Long addVote(VoteSaveDTO voteSaveDTO);

    Map<Long, Integer> getArtistVotes();

    Map<Long, Integer> getGenreVotes();

    List<VoteResultDTO> getVotes();
}

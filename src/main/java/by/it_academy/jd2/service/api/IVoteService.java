package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dto.vote.VoteSaveDTO;

import java.util.Map;

public interface IVoteService {
    void create(VoteSaveDTO vote);
    Map<String, Object> getResults();
}

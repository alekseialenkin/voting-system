package ru.votesystem.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.votesystem.model.Vote;
import ru.votesystem.repository.vote.VoteRepository;

import java.util.List;

import static ru.votesystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote get(int id) {
        return checkNotFoundWithId(voteRepository.get(id), id);
    }

    public Vote vote(Vote vote, int restaurantId, int userId) {
        Assert.notNull(vote, "vote must not be null");
        return voteRepository.save(vote, restaurantId, userId);
    }

    public List<Vote> getAll(int restaurantId) {
        return voteRepository.getAll(restaurantId);
    }

    public Vote update(Vote vote, int restaurantId, int id, int userId) {
        Assert.notNull(vote, "vote must not be null");
        return voteRepository.save(vote, restaurantId, userId);
    }
}

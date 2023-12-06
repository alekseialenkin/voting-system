package ru.votesystem.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.votesystem.model.Vote;
import ru.votesystem.repository.RestaurantRepository;
import ru.votesystem.repository.UserRepository;
import ru.votesystem.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class VoteService {
    private static final LocalTime DEAD_LINE = LocalTime.of(11, 0);

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public Vote vote(Vote vote, int restaurantId, int userId) {
        if (!vote.isNew() && vote.getVoted().toLocalTime().isAfter(DEAD_LINE)) {
            return null;
        } else if (!vote.isNew() && LocalDate.now().equals(vote.getVoted().toLocalDate())) {
            return null;
        }
        Assert.notNull(vote, "vote must not be null");
        vote.setUser(userRepository.getExisted(userId));
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return voteRepository.save(vote);
    }
}

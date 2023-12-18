package ru.votesystem.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.votesystem.error.VoteDeadLineException;
import ru.votesystem.model.Vote;
import ru.votesystem.repository.RestaurantRepository;
import ru.votesystem.repository.UserRepository;
import ru.votesystem.repository.VoteRepository;

import java.time.Clock;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class VoteService {
    private static final LocalTime DEAD_LINE = LocalTime.of(11, 0);

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    @Setter
    private Clock clock = Clock.systemDefaultZone();

    public Vote vote(Vote vote, int userId, int restaurantId) {
        if (LocalTime.now(clock).isAfter(DEAD_LINE)) {
            throw new VoteDeadLineException("You can't vote after 11:00");
        } else if (voteRepository.update(restaurantId, userId) != 0) {
            return voteRepository.getByUser(userId);
        }
        vote.setUser(userRepository.getExisted(userId));
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return voteRepository.save(vote);
    }
}

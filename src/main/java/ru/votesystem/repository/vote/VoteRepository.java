package ru.votesystem.repository.vote;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.votesystem.model.Vote;
import ru.votesystem.repository.restaurant.CrudRestaurantRepository;
import ru.votesystem.repository.user.CrudUserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class VoteRepository {
    private static final LocalTime DEAD_LINE = LocalTime.of(11, 0);

    private final CrudVoteRepository voteRepository;

    private final CrudRestaurantRepository restaurantRepository;

    private final CrudUserRepository userRepository;

    public VoteRepository(CrudVoteRepository voteRepository, CrudRestaurantRepository restaurantRepository, CrudUserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Vote save(Vote vote, int restaurantId, int userId) {
        if (!vote.isNew() && get(vote.id()) == null) {
            return null;
        } else if (!vote.isNew() && vote.getVoted().toLocalTime().isAfter(DEAD_LINE)) {
            return null;
        } else if (!vote.isNew() && LocalDate.now().equals(vote.getVoted().toLocalDate())) {
            return null;
        }
        return voteRepository.save(
                new Vote(userRepository.getReferenceById(userId), restaurantRepository.getReferenceById(restaurantId), vote.getVoted()));
    }
    public Vote get(int id) {
        return voteRepository.findById(id).orElse(null);
    }

    public List<Vote> getAll(int restaurantId) {
        return voteRepository.getAll(restaurantId);
    }

    public boolean delete(int id, int restaurantId) {
        return voteRepository.delete(id, restaurantId) != 0;
    }

}

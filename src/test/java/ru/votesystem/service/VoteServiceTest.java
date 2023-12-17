package ru.votesystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.votesystem.VoteTestData;
import ru.votesystem.model.Vote;
import ru.votesystem.repository.VoteRepository;

import java.time.LocalDateTime;

import static ru.votesystem.RestaurantTestData.*;
import static ru.votesystem.UserTestData.USER_ID;
import static ru.votesystem.UserTestData.user;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;
    @Autowired
    private VoteRepository repository;

    @Test
    void vote() {
        Vote vote = service.vote(new Vote(user, rest2, LocalDateTime.now()),
                REST2_ID, USER_ID);
       service.vote(new Vote(), REST2_ID, USER_ID);
       Assertions.assertThrows(DataIntegrityViolationException.class, ()->repository.getAllForRestaurants(REST2_ID));
        Assertions.assertNull(service.vote(vote, REST3_ID, USER_ID));
    }

    @Test
    void getAll() {
        Assertions.assertEquals(2, repository.getAllForRestaurants(REST1_ID).size());
    }

    @Test
    void voteAfterDeadline() {
        Vote voted = service.vote(VoteTestData.VOTE_DEAD_LINE, REST3_ID, USER_ID);
        Assertions.assertNotNull(service.vote(voted, REST2_ID, USER_ID));
        voted.setVoted(voted.getVoted().plusHours(3));
        Assertions.assertNull(service.vote(voted, REST2_ID, USER_ID));
    }
}
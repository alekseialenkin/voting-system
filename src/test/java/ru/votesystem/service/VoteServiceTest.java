package ru.votesystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.votesystem.error.VoteDeadLineException;
import ru.votesystem.model.Vote;
import ru.votesystem.repository.VoteRepository;

import static ru.votesystem.RestaurantTestData.*;
import static ru.votesystem.UserTestData.USER_ID;
import static ru.votesystem.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;
    @Autowired
    private VoteRepository repository;

    @BeforeEach
    void init() {
        service.setClock(BEFORE_DEAD_LINE);
    }

    @Test
    void vote() {
        Vote newVote = service.vote(new Vote(), USER_ID, REST2_ID);
        Assertions.assertNotNull(newVote);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateVote() {
        Vote newVote = service.vote(new Vote(), USER_ID, REST2_ID);
        Vote updatedVote = service.update(new Vote(), USER_ID, REST3_ID);
        Assertions.assertNotNull(updatedVote);
    }

    @Test
    void getAll() {
        Assertions.assertEquals(2, repository.getAllForRestaurants(REST1_ID).size());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateAfterDeadLine() {
        service.setClock(AFTER_DEAD_LINE);
        Assertions.assertThrows(VoteDeadLineException.class, () -> service.update(new Vote(), USER_ID, REST2_ID));
    }
}
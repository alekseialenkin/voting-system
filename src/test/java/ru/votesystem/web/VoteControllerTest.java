package ru.votesystem.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.votesystem.model.Vote;
import ru.votesystem.repository.VoteRepository;
import ru.votesystem.util.JsonUtil;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.votesystem.RestaurantTestData.REST1_ID;
import static ru.votesystem.RestaurantTestData.REST3_ID;
import static ru.votesystem.UserTestData.USER_MAIL;
import static ru.votesystem.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository repository;

    private final static String REST_URL = "/rest/profile/restaurant/{restaurantId}/vote";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, REST1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(VOTE_MATCHER.contentJson(vote1, vote2));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        Vote newVote = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL, REST3_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content((JsonUtil.writeValue(newVote))))
                .andDo(print())
                .andExpect(status().isOk());

        Vote created = VOTE_MATCHER.readFromJson(actions);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.getExisted(newId), newVote);
    }

    @Test
    void voteDeadLine() throws Exception {
        Vote newVote = new Vote(vote1.id(), LocalDateTime.parse("2023-12-01T11:30:00"));
        perform(MockMvcRequestBuilders.put(REST_URL + vote1.id(), REST3_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content((JsonUtil.writeValue(newVote))));
        Assertions.assertEquals(vote1.getVoted(), repository.getExisted(vote1.id()).getVoted());
    }
}
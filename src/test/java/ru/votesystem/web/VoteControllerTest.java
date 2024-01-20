package ru.votesystem.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.votesystem.model.Vote;
import ru.votesystem.service.VoteService;
import ru.votesystem.util.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.votesystem.RestaurantTestData.*;
import static ru.votesystem.UserTestData.USER_MAIL;
import static ru.votesystem.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteService service;

    private final static String REST_URL = "/rest/votes";

    @BeforeEach
    void init() {
        service.setClock(BEFORE_DEAD_LINE);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + REST1_ID))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        Vote newVote = new Vote();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL + "/" + REST2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content((JsonUtil.writeValue(newVote))))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(actions);
        Assertions.assertNotNull(created);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfterDeadLine() throws Exception {
        service.setClock(AFTER_DEAD_LINE);
        Vote newVote = new Vote();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + REST2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content((JsonUtil.writeValue(newVote))))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }
}
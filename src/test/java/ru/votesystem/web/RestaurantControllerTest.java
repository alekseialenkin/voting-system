package ru.votesystem.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.votesystem.RestaurantTestData;
import ru.votesystem.error.NotFoundException;
import ru.votesystem.model.Restaurant;
import ru.votesystem.repository.RestaurantRepository;
import ru.votesystem.util.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.votesystem.RestaurantTestData.*;
import static ru.votesystem.UserTestData.ADMIN_MAIL;
import static ru.votesystem.UserTestData.USER_MAIL;

class RestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    private RestaurantRepository repository;

    private static final String PROFILE_REST_URL = "/rest/profile/restaurant";
    private static final String ADMIN_REST_URL = "/rest/admin/restaurant";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RESTAURANT_MATCHER.contentJson(rest1, rest2, rest3));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.post(PROFILE_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createNot() throws Exception {
        perform(MockMvcRequestBuilders.post(RestaurantController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL + "/" + REST1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RESTAURANT_MATCHER.contentJson(rest1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL + "/" + REST1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> repository.getExisted(REST1_ID));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL + "/" + REST1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(repository.getExisted(REST1_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRest = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(ADMIN_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRest)))
                .andDo(print())
                .andExpect(status().isOk());

        Restaurant restaurant = RESTAURANT_MATCHER.readFromJson(action);
        int newId = restaurant.id();
        newRest.setId(newId);
        RESTAURANT_MATCHER.assertMatch(repository.getExisted(newId), newRest);
        RESTAURANT_MATCHER.assertMatch(restaurant, newRest);
    }
}
package ru.votesystem.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.votesystem.model.Restaurant;
import ru.votesystem.service.RestaurantService;
import ru.votesystem.util.exception.NotFoundException;
import ru.votesystem.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.votesystem.RestaurantTestData.*;
import static ru.votesystem.TestUtil.userHttpBasic;
import static ru.votesystem.UserTestData.admin;
import static ru.votesystem.UserTestData.user;

class RestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    private RestaurantService service;

    private static final String PROFILE_REST_URL = "/rest/profile/restaurant/";
    private static final String ADMIN_REST_URL = "/rest/admin/restaurant/";

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RESTAURANT_MATCHER.contentJson(rest1, rest2, rest3));
    }

    @Test
    void createNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.post(ADMIN_REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL + REST1_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RESTAURANT_MATCHER.contentJson(rest1));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL + REST1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(REST1_ID));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL + REST1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(service.get(REST1_ID), updated);
    }

    @Test
    void create() throws Exception {
        Restaurant newRest = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(ADMIN_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newRest)))
                .andDo(print())
                .andExpect(status().isOk());

        Restaurant restaurant = RESTAURANT_MATCHER.readFromJson(action);
        int newId = restaurant.id();
        newRest.setId(newId);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRest);
        RESTAURANT_MATCHER.assertMatch(restaurant, newRest);
    }
}
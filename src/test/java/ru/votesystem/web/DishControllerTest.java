package ru.votesystem.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.votesystem.DishTestData;
import ru.votesystem.model.Dish;
import ru.votesystem.service.DishService;
import ru.votesystem.util.exception.NotFoundException;
import ru.votesystem.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.votesystem.DishTestData.*;
import static ru.votesystem.RestaurantTestData.*;
import static ru.votesystem.TestUtil.userHttpBasic;
import static ru.votesystem.UserTestData.admin;
import static ru.votesystem.UserTestData.user;

class DishControllerTest extends AbstractControllerTest {
    @Autowired
    private DishService service;

    private final String PROFILE_REST_URL = "/rest/profile/restaurant/{restaurantId}/dish/";

    private final String ADMIN_REST_URL = "/rest/admin/restaurant/{restaurantId}/dish/";

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL, REST1_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1, dish2));
    }

    @Test
    void createNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.post(ADMIN_REST_URL, REST1_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL + DISH3_ID, REST2_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andExpect(DISH_MATCHER.contentJson(dish3));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL + DISH3_ID, REST2_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(DISH3_ID, REST2_ID));
    }

    @Test
    void create() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(ADMIN_REST_URL, REST3_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isOk());
        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(created.id(), REST3_ID), newDish);
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL + DISH1_ID, REST1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        updated.setId(DISH1_ID);
        DISH_MATCHER.assertMatch(service.get(DISH1_ID, REST1_ID), updated);
    }
}
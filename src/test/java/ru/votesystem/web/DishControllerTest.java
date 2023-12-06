package ru.votesystem.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.votesystem.DishTestData;
import ru.votesystem.error.NotFoundException;
import ru.votesystem.model.Dish;
import ru.votesystem.repository.DishRepository;
import ru.votesystem.util.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.votesystem.DishTestData.*;
import static ru.votesystem.RestaurantTestData.*;
import static ru.votesystem.UserTestData.ADMIN_MAIL;
import static ru.votesystem.UserTestData.USER_MAIL;

class DishControllerTest extends AbstractControllerTest {
    @Autowired
    private DishRepository repository;

    private final String PROFILE_REST_URL = "/rest/profile/restaurant/{restaurantId}/dish";

    private final String ADMIN_REST_URL = "/rest/admin/restaurant/{restaurantId}/dish";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL, REST1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1, dish2));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.post(PROFILE_REST_URL, REST1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(DishTestData.getNew())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createNot() throws Exception {
        perform(MockMvcRequestBuilders.post(RestaurantController.REST_URL, REST2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(DishTestData.getNew())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL + "/" + DISH3_ID, REST2_ID))
                .andExpect(status().isOk())
                .andExpect(DISH_MATCHER.contentJson(dish3));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL + "/" + DISH3_ID, REST2_ID))
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> repository.getExisted(DISH3_ID));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(ADMIN_REST_URL, REST3_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isOk());
        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getExisted(created.id()), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        updated.setId(null);

        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL + "/" + DISH1_ID, REST1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        updated.setId(DISH1_ID);
        DISH_MATCHER.assertMatch(repository.getExisted(DISH1_ID), updated);
    }
}
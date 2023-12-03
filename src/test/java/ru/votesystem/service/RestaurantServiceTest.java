package ru.votesystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.votesystem.model.Restaurant;
import ru.votesystem.util.exception.NotFoundException;

import java.util.List;

import static ru.votesystem.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    private RestaurantService service;

    @Test
    void create() {
        Restaurant created = service.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void get() {
        Restaurant actual = service.get(REST2_ID);
        RESTAURANT_MATCHER.assertMatch(actual, rest2);
    }

    @Test
    void delete() {
        service.delete(REST3_ID);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(REST3_ID));
    }

    @Test
    void getAll() {
        List<Restaurant> restaurants = service.getAll();
        RESTAURANT_MATCHER.assertMatch(restaurants, rest1, rest2, rest3);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        RESTAURANT_MATCHER.assertMatch(service.get(REST1_ID), getUpdated());
    }
}
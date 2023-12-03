package ru.votesystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import ru.votesystem.model.Dish;
import ru.votesystem.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.votesystem.DishTestData.*;
import static ru.votesystem.RestaurantTestData.REST1_ID;

class DishServiceTest extends AbstractServiceTest {
    @Autowired
    private DishService service;

    @Test
    void create() {
        Dish created = service.create(getNew(), REST1_ID);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId, REST1_ID), newDish);
    }

    @Test
    void get() {
        DISH_MATCHER.assertMatch(service.get(DISH1_ID, REST1_ID), dish1);
    }

    @Test
    void delete() {
        service.delete(DISH1_ID, REST1_ID);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(DISH1_ID, REST1_ID));
    }

    @Test
    void getAll() {
        List<Dish> dishes = service.getAll(REST1_ID);
        DISH_MATCHER.assertMatch(dishes, dish1, dish2);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, REST1_ID);
        DISH_MATCHER.assertMatch(service.get(DISH1_ID, REST1_ID), getUpdated());
    }
}
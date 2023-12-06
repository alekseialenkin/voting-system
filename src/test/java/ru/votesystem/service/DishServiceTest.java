package ru.votesystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.votesystem.error.NotFoundException;
import ru.votesystem.model.Dish;
import ru.votesystem.repository.DishRepository;

import java.util.List;

import static ru.votesystem.DishTestData.*;
import static ru.votesystem.RestaurantTestData.REST1_ID;

class DishServiceTest extends AbstractServiceTest {
    @Autowired
    private DishService service;
    @Autowired
    private DishRepository repository;

    @Test
    void create() {
        Dish created = service.create(getNew(), REST1_ID);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getExisted(newId), newDish);
    }

    @Test
    void get() {
        DISH_MATCHER.assertMatch(repository.getExisted(DISH1_ID), dish1);
    }

    @Test
    void delete() {
        repository.delete(DISH1_ID);
        Assertions.assertThrows(NotFoundException.class, () -> repository.getExisted(DISH1_ID));
    }

    @Test
    void getAll() {
        List<Dish> dishes = repository.getAll(REST1_ID);
        DISH_MATCHER.assertMatch(dishes, dish1, dish2);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.create(updated, REST1_ID);
        DISH_MATCHER.assertMatch(repository.getExisted(DISH1_ID), getUpdated());
    }
}
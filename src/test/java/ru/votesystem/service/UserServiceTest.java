package ru.votesystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.votesystem.model.User;
import ru.votesystem.util.exception.NotFoundException;

import java.util.List;

import static ru.votesystem.UserTestData.*;

class UserServiceTest extends AbstractServiceTest {
    @Autowired
    private UserService service;

    @Test
    void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        Assertions.assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void get() {
        User actual = service.get(ADMIN_ID);
        USER_MATCHER.assertMatch(actual, admin);
    }

    @Test
    void getAll() {
        List<User> users = service.getAll();
        USER_MATCHER.assertMatch(users, admin, user);
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }
}
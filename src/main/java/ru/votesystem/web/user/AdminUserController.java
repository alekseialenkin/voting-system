package ru.votesystem.web.user;

import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.votesystem.model.User;

import java.util.List;


@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController extends AbstractUserController {
    public static final String REST_URL = "/rest/users";

    @GetMapping
    @CacheEvict(value = "users", allEntries = true)
    public List<User> getAll() {
        log.info("getAll()");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @CacheEvict(value = "users", allEntries = true)
    public User get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "users", allEntries = true)
    public User create(@Valid @RequestBody User user) {
        return super.create(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "users", allEntries = true)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "users", allEntries = true)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        super.update(user, id);
    }
}

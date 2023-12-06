package ru.votesystem.web.user;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.votesystem.model.User;
import ru.votesystem.repository.UserRepository;

import static ru.votesystem.util.validation.ValidationUtil.assureIdConsistent;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserRepository repository;

    public User get(int id) {
        return repository.getExisted(id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }

    public User create(User user) {
        log.info("create new {}", user);
        return repository.prepareAndSave(user);
    }

    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        log.info("update {}", id);
        assureIdConsistent(user, id);
        repository.prepareAndSave(user);
    }
}

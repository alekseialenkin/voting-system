package ru.votesystem.repository.user;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.votesystem.model.User;

import java.util.List;

@Repository
public class UserRepository {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");
    private final CrudUserRepository crudRepository;

    public UserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public User save(User user) {
        return crudRepository.save(user);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    public User getWithRestaurant(int id) {
        return crudRepository.getWithRestaurant(id);
    }

    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }
}

package ru.votesystem.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.votesystem.model.Dish;
import ru.votesystem.repository.dish.DishRepository;

import java.util.List;

import static ru.votesystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {
    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public Dish create(Dish dish, int resId) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish, resId);
    }

    public Dish get(int id, int restId) {
        return checkNotFoundWithId(repository.get(id, restId), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public List<Dish> getAll(int restId) {
        return repository.getAll(restId);
    }

    public void update(Dish dish, int restId) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish, restId), dish.id());
    }
}

package ru.votesystem.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.votesystem.model.Restaurant;
import ru.votesystem.model.Vote;
import ru.votesystem.repository.restaurant.RestaurantRepository;

import java.util.List;

import static ru.votesystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {
    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    public List<Vote> getAllVotes(int restaurantId){
        return repository.getAllVotes(restaurantId);
    }
}

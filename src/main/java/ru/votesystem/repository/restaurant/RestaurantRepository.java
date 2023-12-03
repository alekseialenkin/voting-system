package ru.votesystem.repository.restaurant;

import org.springframework.stereotype.Repository;
import ru.votesystem.model.Restaurant;
import ru.votesystem.model.Vote;

import java.util.List;

@Repository
public class RestaurantRepository {
    private final CrudRestaurantRepository restaurantRepository;

    public RestaurantRepository(CrudRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant save(Restaurant r) {
        return restaurantRepository.save(r);
    }

    public Restaurant get(int id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public boolean delete(int id) {
        return restaurantRepository.delete(id) != 0;
    }

    public List<Vote> getAllVotes(int restaurantId) {
        return restaurantRepository.getAllVotes(restaurantId);
    }
}

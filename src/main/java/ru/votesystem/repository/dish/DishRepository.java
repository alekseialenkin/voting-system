package ru.votesystem.repository.dish;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.votesystem.model.Dish;
import ru.votesystem.repository.restaurant.CrudRestaurantRepository;

import java.util.List;

@Repository
public class DishRepository {
    private final CrudDishRepository dishRepository;

    private final CrudRestaurantRepository restaurantRepository;

    public DishRepository(CrudDishRepository dishRepository, CrudRestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Dish save(Dish dish, int restId) {
        if (!dish.isNew() && get(dish.id(), restId) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepository.getReferenceById(restId));
        return dishRepository.save(dish);
    }

    @Transactional
    public Dish get(int id, int restId) {
        return dishRepository.findById(id)
                .filter(dish -> dish.getRestaurant().id() == restId)
                .orElse(null);
    }

    public List<Dish> getAll(int restId) {
        return dishRepository.getAll(restId);
    }

    public boolean delete(int id, int restId) {
        return dishRepository.delete(id, restId) != 0;
    }
}

package ru.votesystem.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.votesystem.model.Dish;
import ru.votesystem.repository.DishRepository;
import ru.votesystem.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Dish create(Dish dish, int resId) {
        dish.setRestaurant(restaurantRepository.getExisted(resId));
        return dishRepository.save(dish);
    }
}

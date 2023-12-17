package ru.votesystem.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.votesystem.model.Restaurant;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

}

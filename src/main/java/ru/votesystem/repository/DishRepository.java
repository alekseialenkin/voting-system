package ru.votesystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.votesystem.error.DataConflictException;
import ru.votesystem.model.Dish;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    @Query("SELECT d FROM Dish d WHERE d.id=:id and d.restaurant.id=:restId")
    Optional<Dish> get(@Param("id") int id, @Param("restId") int restId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restId")
    List<Dish> getAll(@Param("restId") int restId);

    @Query("SELECT d from Dish d WHERE d.restaurant.id=:restId and d.date=current date")
    List<Dish> getAllToday(@Param("restId") int restId);

    default Dish getExistedOrBelonged(int restId, int id) {
        return get(id, restId).orElseThrow(
                () -> new DataConflictException("Dish id=" + id + "   is not exist or doesn't belong to Restaurant id=" + restId));
    }
}

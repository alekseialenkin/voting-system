package ru.votesystem.repository.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.votesystem.model.Dish;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restId")
    int delete(@Param("id") int id, @Param("restId") int restId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restId")
    List<Dish> getAll(@Param("restId") int restId);
}

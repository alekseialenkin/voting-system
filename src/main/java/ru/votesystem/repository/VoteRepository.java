package ru.votesystem.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.votesystem.model.Vote;

import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restId")
    List<Vote> getAllForRestaurants(@Param("restId") int restId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId and v.voted=current date")
    Vote getByUser(@Param("userId") int userId);

    @Modifying
    @Query("UPDATE Vote v SET v.restaurant.id=:restaurantId WHERE v.user.id=:userId and v.voted=current date")
    int update(@Param("restaurantId") int restaurantId, @Param("userId") int userId);
}

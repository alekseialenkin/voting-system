package ru.votesystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.votesystem.error.DataConflictException;
import ru.votesystem.model.Vote;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restId")
    List<Vote> getAll(@Param("restId") int restId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restId and v.user.id=:userId")
    Optional<Vote> get(@Param("restId") int restId, @Param("userId")int userId);

    default Vote getExistedOrBelonged(int restId, int userId) {
        return get(restId, userId).orElseThrow(
                () -> new DataConflictException("Dish with restaurant id=" + restId + "   is not exist or doesn't belong to User id=" + userId));
    }
}

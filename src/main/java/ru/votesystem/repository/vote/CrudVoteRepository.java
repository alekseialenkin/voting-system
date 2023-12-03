package ru.votesystem.repository.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.votesystem.model.Vote;

import java.util.List;
@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restId")
    List<Vote> getAll(@Param("restId") int restId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.restaurant.id=:restId")
    int delete(@Param("id") int id, @Param("restId") int restId);
}

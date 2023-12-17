package ru.votesystem.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.votesystem.model.Vote;
import ru.votesystem.repository.VoteRepository;
import ru.votesystem.service.VoteService;

import java.util.List;

import static ru.votesystem.util.validation.ValidationUtil.assureIdConsistent;
import static ru.votesystem.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    public static final String REST_URL = "/rest/votes";

    private VoteService service;
    private VoteRepository repository;

    @GetMapping("/{restaurantId}")
    public List<Vote> getAllForRestaurants(@PathVariable int restaurantId) {
        log.info("getAll votes for restaurant {}", restaurantId);
        return repository.getAllForRestaurants(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote vote(@Valid @RequestBody Vote vote, @PathVariable int restaurantId, @AuthenticationPrincipal AuthorizedUser user) {
        log.info("user {} vote {} for restaurant {}", user.id(), vote, restaurantId);
        checkNew(vote);
        return service.vote(vote, restaurantId, user.id());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote update(@Valid @RequestBody Vote vote, @PathVariable int restaurantId,
                       @PathVariable int id, @AuthenticationPrincipal AuthorizedUser user) {
        log.info("user {} update vote {} for restaurant {}", user.id(), id, restaurantId);
        assureIdConsistent(vote, id);
        return service.vote(vote, restaurantId, user.id());
    }

    @GetMapping("/{userId}")
    public List<Vote> getAllForUser(@PathVariable int userId) {
        log.info("get all votes for user {}", userId);
        return repository.getAllForUser(userId);
    }
}

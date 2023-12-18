package ru.votesystem.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.votesystem.model.Vote;
import ru.votesystem.repository.VoteRepository;
import ru.votesystem.service.VoteService;

import java.net.URI;
import java.util.List;

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

    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> vote(@Valid @RequestBody Vote vote, @PathVariable int restaurantId, @AuthenticationPrincipal AuthorizedUser user) {
        log.info("user {} vote for restaurant {}", user.id(), restaurantId);
        Vote created = service.vote(vote, user.id(), restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);

    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Vote vote, @PathVariable int restaurantId, @AuthenticationPrincipal AuthorizedUser user) {
        log.info("user {} update for restaurant {}", user.id(), restaurantId);
        service.vote(vote, user.id(), restaurantId);
    }
}

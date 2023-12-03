package ru.votesystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.votesystem.AuthorizedUser;
import ru.votesystem.model.Vote;
import ru.votesystem.service.VoteService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

import static ru.votesystem.util.ValidationUtil.assureIdConsistent;
import static ru.votesystem.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    public static final String REST_URL = "/**/restaurant/{restaurantId}/vote";

    @Autowired
    private VoteService service;

    @GetMapping
    public List<Vote> getAll(@PathVariable int restaurantId) {
        return service.getAll(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote vote(@Valid @RequestBody Vote vote, @PathVariable int restaurantId, @ApiIgnore @AuthenticationPrincipal AuthorizedUser user) {
        checkNew(vote);
        return service.vote(vote, restaurantId, user.getId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote update(@Valid @RequestBody Vote vote, @PathVariable int restaurantId,
                       @PathVariable int id, @ApiIgnore @AuthenticationPrincipal AuthorizedUser user) {
        assureIdConsistent(vote, id);
        return service.update(vote, restaurantId, id, user.getId());
    }
}

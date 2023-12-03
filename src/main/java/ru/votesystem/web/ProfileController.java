package ru.votesystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.votesystem.AuthorizedUser;
import ru.votesystem.model.User;
import ru.votesystem.service.UserService;
import ru.votesystem.util.ValidationUtil;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
    static final String REST_URL = "/rest/profile";
    @Autowired
    private UserService service;

    @GetMapping
    public User get(@ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        return service.get(authUser.getId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        service.delete(authUser.getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User created = service.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        ValidationUtil.assureIdConsistent(user, authUser.getId());
        service.update(user);
    }
}

package ru.votesystem.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.votesystem.model.Restaurant;
import ru.votesystem.repository.RestaurantRepository;

import java.util.List;

import static ru.votesystem.util.validation.ValidationUtil.assureIdConsistent;
import static ru.votesystem.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    public static final String REST_URL = "/rest/restaurants";

    private RestaurantRepository repository;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("restaurant {}", id);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant create(@Valid @RequestBody Restaurant restaurant) {
        log.info("update {}", restaurant);
        checkNew(restaurant);
        return repository.save(restaurant);
    }
}

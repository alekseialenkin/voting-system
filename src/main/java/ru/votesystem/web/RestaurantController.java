package ru.votesystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.votesystem.model.Restaurant;
import ru.votesystem.service.RestaurantService;
import ru.votesystem.util.ValidationUtil;

import javax.validation.Valid;
import java.util.List;

import static ru.votesystem.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    public static final String REST_URL = "/**/restaurant";

    @Autowired
    private RestaurantService service;

    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        ValidationUtil.assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant create(@Valid @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        return service.create(restaurant);
    }
}

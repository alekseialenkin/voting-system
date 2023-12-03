package ru.votesystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.votesystem.model.Dish;
import ru.votesystem.service.DishService;

import javax.validation.Valid;
import java.util.List;

import static ru.votesystem.util.ValidationUtil.assureIdConsistent;
import static ru.votesystem.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    public static final String REST_URL = "/**/restaurant/{restaurantId}/dish";

    @Autowired
    private DishService service;

    @GetMapping
    public List<Dish> getAll(@PathVariable int restaurantId) {
        return service.getAll(restaurantId);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id, @PathVariable int restaurantId) {
        return service.get(id, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        service.delete(id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dish create(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        checkNew(dish);
        return service.create(dish, restaurantId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int id) {
        assureIdConsistent(dish, id);
        service.update(dish, restaurantId);
    }
}

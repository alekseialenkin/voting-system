package ru.votesystem.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.votesystem.model.Dish;
import ru.votesystem.repository.DishRepository;
import ru.votesystem.service.DishService;

import java.util.List;

import static ru.votesystem.util.validation.ValidationUtil.assureIdConsistent;
import static ru.votesystem.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {
    public static final String REST_URL = "/rest/restaurants/{restaurantId}/dishes";

    private DishService service;
    private DishRepository repository;

    @GetMapping
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("getAll dishes for restaurant {}", restaurantId);
        return repository.getAll(restaurantId);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return repository.getExistedOrBelonged(restaurantId, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete dish {} in restaurant {}", id, restaurantId);
        Dish dish = repository.getExistedOrBelonged(restaurantId, id);
        repository.delete(dish);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dish create(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create dish {} in restaurant {}", dish, restaurantId);
        checkNew(dish);
        return service.create(dish, restaurantId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update dish {} in restaurant {}", dish, restaurantId);
        assureIdConsistent(dish, id);
        repository.getExistedOrBelonged(restaurantId, id);
        service.create(dish, restaurantId);
    }
}

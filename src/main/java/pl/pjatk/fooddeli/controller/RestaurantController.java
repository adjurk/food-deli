package pl.pjatk.fooddeli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.fooddeli.model.Restaurant;
import pl.pjatk.fooddeli.service.RestaurantService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> findAll() {
        return ResponseEntity.ok(restaurantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Restaurant>> findRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> foundRestaurant = restaurantService.findRestaurant(id);
        if (!foundRestaurant.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundRestaurant);
    }

    @PostMapping
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.save(restaurant));
    }

}

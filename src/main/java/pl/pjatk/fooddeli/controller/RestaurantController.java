package pl.pjatk.fooddeli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.fooddeli.model.Food;
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
        if (!restaurantService.findRestaurant(id).isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(restaurantService.findRestaurant(id));
        }
    }

    @PostMapping
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.save(restaurant));
    }

//    @PostMapping("/food")
//    public ResponseEntity<Food> addFoodToRestaurant(@RequestBody Food food,)

}

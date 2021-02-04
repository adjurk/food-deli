package pl.pjatk.fooddeli.service;

import org.springframework.stereotype.Service;
import pl.pjatk.fooddeli.model.Food;
import pl.pjatk.fooddeli.model.Restaurant;
import pl.pjatk.fooddeli.repository.RestaurantRepository;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Optional<Restaurant> findRestaurant (Long id) {
        Optional<Restaurant> foundRestaurant = restaurantRepository.findById(id);
        if (!foundRestaurant.isPresent()) {
            throw new ValidationException("Restaurant with specified id was not found.");
        }
        return foundRestaurant;
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant save(Restaurant restaurant){
        List<Food> foods = restaurant.getFoods();
        for (Food food : foods) {
            food.setRestaurant(restaurant);
        }
        return restaurantRepository.save(restaurant);
    }
}

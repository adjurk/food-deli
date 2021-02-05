package pl.pjatk.fooddeli.service;

import org.springframework.stereotype.Service;
import pl.pjatk.fooddeli.model.Food;
import pl.pjatk.fooddeli.repository.FoodRepository;

import java.util.Optional;

@Service
public class FoodService {
    FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Optional<Food> findFood(Long id) {
        return foodRepository.findById(id);
    }
}

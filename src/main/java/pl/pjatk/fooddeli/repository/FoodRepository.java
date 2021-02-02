package pl.pjatk.fooddeli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pjatk.fooddeli.model.Food;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query("SELECT SUM(f.cost) FROM Food f WHERE f.id IN :idList")
    Float getTotalCostFromFoodOrder(List<Long> idList);
}

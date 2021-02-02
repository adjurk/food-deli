package pl.pjatk.fooddeli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.fooddeli.model.FoodOrder;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
}

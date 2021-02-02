package pl.pjatk.fooddeli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.fooddeli.model.DeliveryPerson;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
}

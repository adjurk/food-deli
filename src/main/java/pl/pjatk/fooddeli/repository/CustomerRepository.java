package pl.pjatk.fooddeli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.fooddeli.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

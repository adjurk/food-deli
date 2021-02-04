package pl.pjatk.fooddeli.service;

import org.springframework.stereotype.Service;
import pl.pjatk.fooddeli.model.Customer;
import pl.pjatk.fooddeli.model.Restaurant;
import pl.pjatk.fooddeli.repository.CustomerRepository;

import javax.validation.ValidationException;
import java.util.Optional;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService (CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save (Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> findCustomer (Long id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);
        if (!foundCustomer.isPresent()) {
            throw new ValidationException("Customer with specified id was not found.");
        }
        return foundCustomer;
    }
}

package pl.pjatk.fooddeli.service;

import org.springframework.stereotype.Service;
import pl.pjatk.fooddeli.exception.OrderValidationException;
import pl.pjatk.fooddeli.model.Customer;
import pl.pjatk.fooddeli.repository.CustomerRepository;

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

    public Optional<Customer> verifyCustomerInOrder (Long id) throws OrderValidationException {
        Optional<Customer> foundCustomer = customerRepository.findById(id);
        if (!foundCustomer.isPresent()) {
            throw new OrderValidationException("Customer with specified id was not found.");
        }
        return foundCustomer;
    }

    public Optional<Customer> findCustomer (Long id) { return customerRepository.findById(id);}
}

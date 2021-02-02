package pl.pjatk.fooddeli.service;

import org.springframework.stereotype.Service;
import pl.pjatk.fooddeli.model.Customer;
import pl.pjatk.fooddeli.repository.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService (CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> findCustomer(Long id){
        return customerRepository.findById(id);
    }

    public Customer save (Customer customer) {
        return customerRepository.save(customer);
    }
}

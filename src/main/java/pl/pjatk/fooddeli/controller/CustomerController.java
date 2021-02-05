package pl.pjatk.fooddeli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.fooddeli.exception.OrderValidationException;
import pl.pjatk.fooddeli.model.Customer;
import pl.pjatk.fooddeli.service.CustomerService;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> getCustomer(@PathVariable Long id) {
        Optional<Customer> foundCustomer = customerService.findCustomer(id);
        if (!foundCustomer.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerService.findCustomer(id));
    }

    @PostMapping
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.save(customer));
    }
}

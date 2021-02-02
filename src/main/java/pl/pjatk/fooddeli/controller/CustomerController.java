package pl.pjatk.fooddeli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.fooddeli.model.Customer;
import pl.pjatk.fooddeli.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    CustomerService customerService;

    public CustomerController (CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity getCustomer(@PathVariable Long id){
        return ResponseEntity.ok(customerService.findCustomer(id));
    }

    @PostMapping
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.save(customer));
    }
}

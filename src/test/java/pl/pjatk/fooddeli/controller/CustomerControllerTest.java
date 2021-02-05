package pl.pjatk.fooddeli.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.pjatk.fooddeli.model.Customer;
import pl.pjatk.fooddeli.repository.CustomerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void shouldNotGetCustomer() throws Exception {
        mockMvc.perform(get("/customer/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetCustomer() throws Exception {
        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(new Customer(1L, "Adam", "Jurkiewicz", "Subisława Gdańsk")));
        mockMvc.perform(get("/customer/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }
}
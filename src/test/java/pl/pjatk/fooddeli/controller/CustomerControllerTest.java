package pl.pjatk.fooddeli.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.pjatk.fooddeli.helpers.JsonFileReader;
import pl.pjatk.fooddeli.model.Customer;
import pl.pjatk.fooddeli.repository.CustomerRepository;
import pl.pjatk.fooddeli.service.CustomerService;

import java.util.Optional;

import static org.mockito.Mockito.when;
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
        String response = "src/test/resources/jsons/GetCustomerResponse.json";
        String responseJson = JsonFileReader.readFileAsString(response);
        mockMvc.perform(get("/customer/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }
}
package pl.pjatk.fooddeli.controller;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.pjatk.fooddeli.helpers.JsonFileReader;
import pl.pjatk.fooddeli.model.Customer;
import pl.pjatk.fooddeli.model.Food;
import pl.pjatk.fooddeli.model.FoodOrder;
import pl.pjatk.fooddeli.model.Restaurant;
import pl.pjatk.fooddeli.service.CustomerService;
import pl.pjatk.fooddeli.service.FoodOrderService;
import pl.pjatk.fooddeli.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FoodOrderControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    FoodOrderService foodOrderService;
    @MockBean
    RestaurantService restaurantService;
    @MockBean
    CustomerService customerService;

    @Test
    void placeOrder() throws Exception {
        Restaurant restaurant = Restaurant.builder().id(1L).name("R One").address("R One Street").isOpen(true).maxDistance(12.0F).deliveryCost(6.0F).build();
        Float actualDeliveryDistance = 10.0F;

        when(restaurantService.verifyRestaurantInOrder(anyLong())).thenReturn(Optional.of(restaurant));
        when(customerService.verifyCustomerInOrder(anyLong())).thenReturn(Optional.of(new Customer()));
        when(foodOrderService.getDistanceFromBingMaps(anyString(),anyString())).thenReturn(actualDeliveryDistance);
        when(foodOrderService.verifyDistance(anyFloat(),anyFloat())).thenReturn(true);
        when(foodOrderService.calculateDeliveryCost(anyFloat(),anyFloat())).thenReturn(12.86F);
        when(foodOrderService.calculateTotalFoodCost(anyList())).thenReturn(10.0F);
        when(foodOrderService.placeOrder(anyObject())).thenReturn(new FoodOrder());

        String request = "src/test/resources/jsons/PlaceOrderRequest.json";
        String requestJson = JsonFileReader.readFileAsString(request);
        String response = "src/test/resources/jsons/PlaceOrderResponse.json";
        String responseJson = JsonFileReader.readFileAsString(response);

        mockMvc.perform(post("/food-order").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }
}
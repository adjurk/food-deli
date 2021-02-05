package pl.pjatk.fooddeli.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjatk.fooddeli.exception.OrderValidationException;
import pl.pjatk.fooddeli.model.Food;
import pl.pjatk.fooddeli.repository.FoodOrderRepository;
import pl.pjatk.fooddeli.repository.FoodRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodOrderServiceTest {

    @InjectMocks
    private FoodOrderService foodOrderService;

    @Mock
    private FoodRepository foodRepository;

    @Test
    void placeOrder() {
    }

    @Test
    void calculateTotalFoodCost() {
    }

    @Test
    void getDistanceFromBingMaps() {
    }

    @Test(expected)
    void shouldVerifyOrderItemsExistThrowException()  {
        //given
        List<Food> foods = new ArrayList<>();
        Food foodOne = new Food();
        Food foodTwo = new Food();
        foodOne.setId(1L);
        foodTwo.setId(50L);
        foods.add(foodOne);
        foods.add(foodTwo);

        when(foodRepository.findById(1L)).thenReturn(Optional.of(new Food()));
        when(foodRepository.findById(50L)).thenReturn(Optional.empty());
        //when
        doThrow(new OrderValidationException(""))
                .when(foodOrderService)
                .verifyOrderItemsExist(foods);
        //then
//        assertThat()
    }

    @Test
    void canVerifyDistance() {
        Float restaurantMaxDeliveryDistance = 10.0f;
        Float actualDeliveryDistance = null;
        Boolean isValidDistance = foodOrderService.verifyDistance(restaurantMaxDeliveryDistance, actualDeliveryDistance);
        assertThat(isValidDistance).isNull();
    }

    @Test
    void shouldExceed3Kilometers() {
        //given
        Float restaurantDeliveryCost = 6.0f;
        Float deliveryDistance = 10.0f;
        //when
        Float totalDeliveryCost = foodOrderService.calculateDeliveryCost(restaurantDeliveryCost, deliveryDistance);
        //then
        assertThat(totalDeliveryCost.equals(12.86f));
    }

    @Test
    void shouldNotExceed3Kilometers() {
        Float restaurantDeliveryCost = 6.0f;
        Float deliveryDistance = 1.43f;

        Float totalDeliveryCost = foodOrderService.calculateDeliveryCost(restaurantDeliveryCost, deliveryDistance);

        assertThat(totalDeliveryCost.equals(6.0f));
    }
}
package pl.pjatk.fooddeli.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.fooddeli.exception.OrderValidationException;
import pl.pjatk.fooddeli.json.DistanceResponse;
import pl.pjatk.fooddeli.model.Food;
import pl.pjatk.fooddeli.model.FoodOrder;
import pl.pjatk.fooddeli.repository.FoodOrderRepository;
import pl.pjatk.fooddeli.repository.FoodRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FoodOrderService {
    FoodOrderRepository foodOrderRepository;
    FoodRepository foodRepository;
    FoodService foodService;

    @Value("${bing.api.key}")
    String bingApiKey;

    public FoodOrderService(FoodOrderRepository foodOrderRepository, FoodRepository foodRepository, FoodService foodService) {
        this.foodOrderRepository = foodOrderRepository;
        this.foodRepository = foodRepository;
        this.foodService = foodService;
    }

    public FoodOrder placeOrder(FoodOrder foodOrder) {
        return foodOrderRepository.save(foodOrder);
    }

    public Float calculateTotalFoodCost(List<Food> orderItems) {
        List<Long> idList = new ArrayList<>();
        for (Food food : orderItems) {
            idList.add(food.getId());
        }
        return foodRepository.getTotalCostFromFoodOrder(idList);
    }

    public Float getDistanceFromBingMaps(String restaurantAddress, String customerAddress) {
        try {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            RestTemplate restTemplate = restTemplateBuilder.build();
            String url = "http://dev.virtualearth.net/REST/V1/Routes/Driving?wp.0={restaurantAddress}&wp.1={customerAddress}&key={bingApiKey}";
            ResponseEntity<DistanceResponse> res = restTemplate.getForEntity(
                    url, DistanceResponse.class, restaurantAddress, customerAddress, bingApiKey);
            return res.getBody().getResourceSets().get(0).getResources().get(0).getTravelDistance(); // TODO: change to Objects.RequireNonNull and write test to check behavior
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    public Boolean verifyDistance(Float restaurantMaxDeliveryDistance, Float actualDeliveryDistance) {
        try {
            return restaurantMaxDeliveryDistance >= actualDeliveryDistance;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void verifyOrderItemsExist(List<Food> orderItems) throws OrderValidationException {
        for (Food food : orderItems) {
            if (!foodService.findFood(food.getId()).isPresent()) {
                throw new OrderValidationException("One of order items provided does not exist.");
            }
        }
    }

    // Delivery up to 3 km is free for all restaurants, method below calculates other cases
    public Float calculateDeliveryCost(Float restaurantDeliveryCost, Float deliveryDistance) {
        if (deliveryDistance > 3) {
            for (float i = 3f; i <= deliveryDistance; i += 0.5f) {
                restaurantDeliveryCost += 0.49f;
            }
        }
        return restaurantDeliveryCost;
    }
}
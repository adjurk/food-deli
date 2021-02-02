package pl.pjatk.fooddeli.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.fooddeli.json.DistanceResponse;
import pl.pjatk.fooddeli.model.Food;
import pl.pjatk.fooddeli.model.FoodOrder;
import pl.pjatk.fooddeli.repository.FoodOrderRepository;
import pl.pjatk.fooddeli.repository.FoodRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodOrderService {
    FoodOrderRepository foodOrderRepository;
    FoodRepository foodRepository;

    @Value("${bing.api.key}")
    String bingApiKey;

    public FoodOrderService(FoodOrderRepository foodOrderRepository, FoodRepository foodRepository) {
        this.foodOrderRepository = foodOrderRepository;
        this.foodRepository = foodRepository;
    }

    public FoodOrder placeOrder(FoodOrder foodOrder){
        return foodOrderRepository.save(foodOrder);
    }

    public float calculateTotalFoodCost(List<Food> orderItems) {
        List<Long> idList = new ArrayList<>();
        for (Food food : orderItems) {
            idList.add(food.getId());
        }
        return foodRepository.getTotalCostFromFoodOrder(idList);
    }

    public Float getDistanceFromBingMaps(String restaurantAddress, String customerAddress){
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = "http://dev.virtualearth.net/REST/V1/Routes/Driving?wp.0={restaurantAddress}&wp.1={customerAddress}&key={bingApiKey}";
        ResponseEntity<DistanceResponse> res = restTemplate.getForEntity(
                url, DistanceResponse.class, restaurantAddress, customerAddress, bingApiKey);
        return res.getBody().getResourceSets().get(0).getResources().get(0).getTravelDistance(); // TODO: change to Objects.RequireNonNull and write test to check behavior
    }

    public boolean verifyDistance(Float restaurantMaxDeliveryDistance, Float actualDeliveryDistance) {
        return restaurantMaxDeliveryDistance >= actualDeliveryDistance;
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
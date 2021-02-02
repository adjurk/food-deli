package pl.pjatk.fooddeli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.fooddeli.model.FoodOrder;
import pl.pjatk.fooddeli.service.FoodOrderService;

@RestController
@RequestMapping("/food-order")
public class FoodOrderController {

    FoodOrderService foodOrderService;

    public FoodOrderController(FoodOrderService foodOrderService) {
        this.foodOrderService = foodOrderService;
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody FoodOrder foodOrder){
        //TODO: validate: customer/restaurant exists, order items are available
        //TODO: calculate time to prepare

        // Check delivery distance - otherwise order won't be delivered anyway
        Float actualDeliveryDistance = foodOrderService.getDistanceFromBingMaps(
                foodOrder.getRestaurant().getAddress(), foodOrder.getCustomer().getAddress());

        if (foodOrderService.verifyDistance(foodOrder.getRestaurant().getMaxDistance(),actualDeliveryDistance)) {

            // Order between accepted delivery distance - calculate total cost
            foodOrder.setTotalCost(foodOrderService.calculateTotalFoodCost(foodOrder.getOrderItems())
                    + foodOrderService.calculateDeliveryCost(foodOrder.getRestaurant().getDeliveryCost(),
                    actualDeliveryDistance));

            // Finally, save order to database
            foodOrderService.placeOrder(foodOrder);

            return ResponseEntity.ok("Order placed! Total cost: " + foodOrder.getTotalCost());
        } else {
            return ResponseEntity.unprocessableEntity().body(
                    "Provided customer distance exceeds restaurant's maximum distance");
        }
    }
}

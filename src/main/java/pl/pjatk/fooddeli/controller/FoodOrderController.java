package pl.pjatk.fooddeli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.fooddeli.json.OrderInformation;
import pl.pjatk.fooddeli.json.PlaceOrderResponse;
import pl.pjatk.fooddeli.model.FoodOrder;
import pl.pjatk.fooddeli.service.CustomerService;
import pl.pjatk.fooddeli.service.FoodOrderService;
import pl.pjatk.fooddeli.service.RestaurantService;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/food-order")
public class FoodOrderController {

    FoodOrderService foodOrderService;
    RestaurantService restaurantService;
    CustomerService customerService;

    public FoodOrderController(FoodOrderService foodOrderService, RestaurantService restaurantService, CustomerService customerService) {
        this.foodOrderService = foodOrderService;
        this.restaurantService = restaurantService;
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<PlaceOrderResponse> placeOrder(@Valid @RequestBody FoodOrder foodOrder){
        //TODO: validate: customer/restaurant exists, order items are available
        //TODO: calculate time to prepare
        PlaceOrderResponse placeOrderResponse = new PlaceOrderResponse();

        try {
            restaurantService.findRestaurant(foodOrder.getRestaurant().getId());
            customerService.findCustomer(foodOrder.getCustomer().getId());
        } catch (ValidationException e) {
            placeOrderResponse.setMessage(e.getMessage());
            return ResponseEntity.status(404).body(placeOrderResponse);
        }

        // Check delivery distance - otherwise order won't be delivered anyway
        Float actualDeliveryDistance = foodOrderService.getDistanceFromBingMaps(
                foodOrder.getRestaurant().getAddress(), foodOrder.getCustomer().getAddress());

        if (foodOrderService.verifyDistance(foodOrder.getRestaurant().getMaxDistance(),actualDeliveryDistance)) {

            OrderInformation orderInformation = new OrderInformation();
            orderInformation.setDeliveryDistance(actualDeliveryDistance);

            // Order between accepted delivery distance - calculate total cost
            Float deliveryCost = foodOrderService.calculateDeliveryCost(foodOrder.getRestaurant().getDeliveryCost(),
                    actualDeliveryDistance);
            orderInformation.setDeliveryCost(deliveryCost);

            Float totalCost = foodOrderService.calculateTotalFoodCost(foodOrder.getOrderItems()) + deliveryCost;
            foodOrder.setTotalCost(totalCost);
            orderInformation.setTotalCost(totalCost);

            // Finally, save order to database
            foodOrderService.placeOrder(foodOrder);
            orderInformation.setOrderItems(foodOrder.getOrderItems());

            placeOrderResponse.setMessage("Order placed successfully!");
            placeOrderResponse.setOrderInformation(orderInformation);
            return ResponseEntity.ok(placeOrderResponse);
        } else {
            placeOrderResponse.setMessage("Provided customer distance exceeds restaurant's maximum distance");
            return ResponseEntity.unprocessableEntity().body(placeOrderResponse);
        }
    }
}

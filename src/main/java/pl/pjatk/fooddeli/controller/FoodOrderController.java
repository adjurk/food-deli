package pl.pjatk.fooddeli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.fooddeli.exception.OrderValidationException;
import pl.pjatk.fooddeli.json.OrderInformation;
import pl.pjatk.fooddeli.json.PlaceOrderResponse;

import pl.pjatk.fooddeli.model.FoodOrder;
import pl.pjatk.fooddeli.model.Restaurant;
import pl.pjatk.fooddeli.service.CustomerService;
import pl.pjatk.fooddeli.service.FoodOrderService;
import pl.pjatk.fooddeli.service.RestaurantService;

import javax.validation.Valid;
import java.util.Optional;

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
    public ResponseEntity<PlaceOrderResponse> placeOrder(@Valid @RequestBody FoodOrder foodOrder) {
        PlaceOrderResponse placeOrderResponse = new PlaceOrderResponse();
        Optional<Restaurant> restaurantFromDb;

        // Check if restaurant, customer, orderItems exist
        try {
            restaurantFromDb = restaurantService.verifyRestaurantInOrder(foodOrder.getRestaurant().getId());
            customerService.verifyCustomerInOrder(foodOrder.getCustomer().getId());
            foodOrderService.verifyOrderItemsExist(foodOrder.getOrderItems());
        } catch (OrderValidationException e) {
            placeOrderResponse.setMessage(e.getMessage());
            return ResponseEntity.status(404).body(placeOrderResponse);
        }

        Float actualDeliveryDistance;

        // Check delivery distance - otherwise order won't be delivered anyway
        try {
            actualDeliveryDistance = foodOrderService.getDistanceFromBingMaps(
                    foodOrder.getRestaurant().getAddress(), foodOrder.getCustomer().getAddress());
        } catch (NullPointerException e) {
            placeOrderResponse.setMessage("Location routing service unavailable, please try again later.");
            return ResponseEntity.status(503).body(placeOrderResponse);
        }

        // Finally, use Bing Maps API to verify distance
        if (foodOrderService.verifyDistance(foodOrder.getRestaurant().getMaxDistance(), actualDeliveryDistance)) {

            OrderInformation orderInformation = new OrderInformation();
            orderInformation.setDeliveryDistance(actualDeliveryDistance);

            // Order is between accepted delivery distance - calculate delivery cost & total cost
            Float deliveryCost = foodOrderService.calculateDeliveryCost(foodOrder.getRestaurant().getDeliveryCost(),
                    actualDeliveryDistance);
            orderInformation.setDeliveryCost(deliveryCost);

            Float totalCost = foodOrderService.calculateTotalFoodCost(foodOrder.getOrderItems()) + deliveryCost;
            foodOrder.setTotalCost(totalCost);
            orderInformation.setTotalCost(totalCost);
            orderInformation.setRestaurantName(restaurantFromDb.get().getName());
            orderInformation.setRestaurantAddress(restaurantFromDb.get().getAddress());

            // Finally, save order to database
            // TODO: format all Floats up to two decimal points

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

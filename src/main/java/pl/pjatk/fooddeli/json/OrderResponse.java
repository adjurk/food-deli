package pl.pjatk.fooddeli.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
    private Float totalCost;
    private Float deliveryCost;
    private Float deliveryDistance;
}

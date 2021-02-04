package pl.pjatk.fooddeli.json;

import lombok.Getter;
import lombok.Setter;
import pl.pjatk.fooddeli.model.Food;

import java.util.List;

@Setter
@Getter
public class OrderInformation {
    private Float totalCost;
    private Float deliveryCost;
    private Float deliveryDistance;
    private List<Food> orderItems;
}

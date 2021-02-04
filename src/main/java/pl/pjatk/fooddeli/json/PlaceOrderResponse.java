package pl.pjatk.fooddeli.json;

import lombok.Getter;
import lombok.Setter;
import pl.pjatk.fooddeli.model.Food;

import java.util.List;

@Getter
@Setter
public class PlaceOrderResponse {
    private String message;
    private OrderInformation orderInformation;
}

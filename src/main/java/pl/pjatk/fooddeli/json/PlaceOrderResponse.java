package pl.pjatk.fooddeli.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderResponse {
    private String message;
    private OrderInformation orderInformation;
}

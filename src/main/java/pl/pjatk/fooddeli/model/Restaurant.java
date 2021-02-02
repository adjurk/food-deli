package pl.pjatk.fooddeli.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private boolean isOpen; // TODO: implement opening days/hours
    @OneToMany(mappedBy = "restaurant", cascade = ALL)
    private List<Food> foods;
    private Float maxDistance;
    private Float deliveryCost;

    public Restaurant(String name, String address, boolean isOpen, List<Food> foods, Float maxDistance, Float deliveryCost) {
        this.name = name;
        this.address = address;
        this.isOpen = isOpen;
        this.foods = foods;
        this.maxDistance = maxDistance;
        this.deliveryCost = deliveryCost;
    }
}

package pl.pjatk.fooddeli.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Restaurant restaurant;
    @ManyToMany
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "food_order_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private List<Food> orderItems;
    private float totalCost;
    private int timeToPrepare;

}
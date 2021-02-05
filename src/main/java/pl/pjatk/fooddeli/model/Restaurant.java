package pl.pjatk.fooddeli.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String address;
    private boolean isOpen; // TODO: implement opening days/hours
    @OneToMany(mappedBy = "restaurant", cascade = ALL)
    @NotEmpty
    private List<@Valid Food> foods;
    @NotNull
    @Positive(message = "Restaurant maximum distance must be greater than 0.") // TODO: how to show message with response?
    private Float maxDistance;
    @NotNull
    @PositiveOrZero
    private Float deliveryCost;
}

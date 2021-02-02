package pl.pjatk.fooddeli.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class DeliveryPerson {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;

}

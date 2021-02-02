package pl.pjatk.fooddeli.json;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
public class DistanceResponse {
    private List<ResourceSet> resourceSets;

}

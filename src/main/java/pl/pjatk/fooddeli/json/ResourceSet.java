package pl.pjatk.fooddeli.json;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ResourceSet {
    private List<Resource> resources;
}
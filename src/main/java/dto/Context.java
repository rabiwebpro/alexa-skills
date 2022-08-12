package dto;

import java.util.ArrayList;
import java.util.List;

public class Context {
    private List<Property> properties = new ArrayList<>();

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}

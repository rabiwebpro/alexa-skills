package dto;

public class Property {
    private String namespace;
    private String instance;
    private String name;
    private Object value;
    private String timeOfSample;
    private Integer uncertaintyInMilliseconds;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getTimeOfSample() {
        return timeOfSample;
    }

    public void setTimeOfSample(String timeOfSample) {
        this.timeOfSample = timeOfSample;
    }

    public Integer getUncertaintyInMilliseconds() {
        return uncertaintyInMilliseconds;
    }

    public void setUncertaintyInMilliseconds(Integer uncertaintyInMilliseconds) {
        this.uncertaintyInMilliseconds = uncertaintyInMilliseconds;
    }
}

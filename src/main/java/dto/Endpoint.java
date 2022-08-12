package dto;

import java.util.List;

public class Endpoint {
    private String endpointId;
    private Scope scope;
    private List<Object> cookie;

    public String getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public List<Object> getCookie() {
        return cookie;
    }

    public void setCookie(List<Object> cookie) {
        this.cookie = cookie;
    }
}

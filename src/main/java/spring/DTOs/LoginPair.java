package spring.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginPair {

    @JsonProperty("status")
    private String status;

    @JsonProperty("client")
    private UserHTTPPojo client;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserHTTPPojo getClient() {
        return client;
    }

    public void setClient(UserHTTPPojo client) {
        this.client = client;
    }
}

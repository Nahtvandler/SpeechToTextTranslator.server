package spring.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginPair loginPair = (LoginPair) o;
        return Objects.equals(status, loginPair.status) &&
                Objects.equals(client, loginPair.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, client);
    }
}

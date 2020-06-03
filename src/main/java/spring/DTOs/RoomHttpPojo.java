package spring.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomHttpPojo {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "owner")
    private UserHTTPPojo owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserHTTPPojo getOwner() {
        return owner;
    }

    public void setOwner(UserHTTPPojo owner) {
        this.owner = owner;
    }
}

package spring.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class UserHTTPPojo {
    @JsonProperty(value = "login")
    private String login;

    @JsonProperty(value = "firstName")
    private String firstName;

    @JsonProperty(value = "middleName")
    private String middleName;

    @JsonProperty(value = "lastName")
    private String lastName;

    @JsonProperty(value = "password")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserHTTPPojo{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHTTPPojo that = (UserHTTPPojo) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, firstName, middleName, lastName, password);
    }
}

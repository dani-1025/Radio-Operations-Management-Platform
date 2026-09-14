package com.RadioManagement.RadioManagement.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthRegRequest {
    String username;
    String password;
    @JsonProperty("role")
    String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

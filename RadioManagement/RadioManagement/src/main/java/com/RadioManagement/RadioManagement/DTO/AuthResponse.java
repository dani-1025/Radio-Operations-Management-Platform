package com.RadioManagement.RadioManagement.DTO;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class AuthResponse {
    private String token;
    int status;


    public AuthResponse() {}
    public AuthResponse(String token, int status) {
        this.token = token;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken(){
        return token;
    }
    public void setToken(String t){
        this.token = t;
    }
}

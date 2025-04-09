package com.pizza.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private boolean firstLogin;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginResponse(String accessToken, boolean firstLogin) {
        this.accessToken = accessToken;
        this.firstLogin = firstLogin;
    }
}

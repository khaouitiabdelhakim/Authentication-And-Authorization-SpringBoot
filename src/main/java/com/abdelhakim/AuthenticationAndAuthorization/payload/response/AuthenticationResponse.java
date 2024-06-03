package com.abdelhakim.AuthenticationAndAuthorization.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class AuthenticationResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private List<String> roles;

    public AuthenticationResponse(String accessToken, Long id, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}

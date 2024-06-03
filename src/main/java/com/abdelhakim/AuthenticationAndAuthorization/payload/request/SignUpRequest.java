package com.abdelhakim.AuthenticationAndAuthorization.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;


    private String role;

    @NotBlank
    @Size(min = 4, max = 40)
    private String password;

}

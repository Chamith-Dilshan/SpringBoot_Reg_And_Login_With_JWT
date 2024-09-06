package com.KaizakiSoftwares.JobPortal.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This Is the Model class for user Log in request
 */
@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @Email(message = "Email not have the right format")
    @NotEmpty(message = "Email should not empty")
    @NotBlank(message = "Email should not blank")
    private String email;
    @NotEmpty(message = "Password should not empty")
    @NotBlank(message = "Password should not blank")
    @Size(min = 8, message = "Password should have 8 or more characters")
    private String password;
}

package com.KaizakiSoftwares.JobPortal.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    // todo - use custom validation annotations

    @NotEmpty(message = "Firstname should not empty")
    @NotBlank(message = "Firstname should not blank")
    private String firstname;
    @NotEmpty(message = "LastName should not empty")
    @NotBlank(message = "LastName should not blank")
    private String lastname;
    @Email(message = "Email not have the right format")
    @NotEmpty(message = "Email should not empty")
    @NotBlank(message = "Email should not blank")
    private String email;
    @NotEmpty(message = "Password should not empty")
    @NotBlank(message = "Password should not blank")
    @Size(min = 8, message = "Password should have 8 or more characters")
    private String password;
    @NotEmpty(message = "Password should not empty")
    @NotBlank(message = "Password should not blank")
    private String role;
}

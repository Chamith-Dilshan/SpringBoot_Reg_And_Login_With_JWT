package com.KaizakiSoftwares.JobPortal.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This Is the Model class for user Log in response
 */
@Getter
@Setter
@Builder
public class AuthenticationResponse {

    private String token;
}

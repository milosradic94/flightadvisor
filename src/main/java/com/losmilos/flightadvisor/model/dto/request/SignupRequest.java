package com.losmilos.flightadvisor.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
public class SignupRequest {

    @NotBlank
    @Size(min = 1, max = 150)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 150)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 8, max = 40)
    private String password;
}

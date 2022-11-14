package com.losmilos.flightadvisor.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class SigninRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}

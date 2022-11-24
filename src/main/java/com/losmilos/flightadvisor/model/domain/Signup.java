package com.losmilos.flightadvisor.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class Signup {

    private String firstName;

    private String lastName;

    private String username;

    private String password;
}

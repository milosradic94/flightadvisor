package com.losmilos.flightadvisor.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class Role {

    private Long id;

    private com.losmilos.flightadvisor.enumeration.Role role;
}

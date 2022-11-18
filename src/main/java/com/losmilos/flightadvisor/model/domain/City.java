package com.losmilos.flightadvisor.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class City {

    private Long id;

    private String name;

    private String country;

    private String description;
}

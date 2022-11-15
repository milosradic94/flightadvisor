package com.losmilos.flightadvisor.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class CityRequest {

    @NotBlank
    @Size(min = 2, max = 150)
    private String name;

    @NotBlank
    @Size(min = 2, max = 150)
    private String country;

    @NotBlank
    @Size(min = 2, max = 2000)
    private String description;
}

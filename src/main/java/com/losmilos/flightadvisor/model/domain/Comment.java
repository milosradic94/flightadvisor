package com.losmilos.flightadvisor.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class Comment {

    private Long id;

    private String description;

    private City city;

    private User user;
}

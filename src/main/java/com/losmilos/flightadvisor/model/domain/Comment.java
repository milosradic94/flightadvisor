package com.losmilos.flightadvisor.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class Comment {

    private Long id;

    private String description;

    private City city;

    private User user;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

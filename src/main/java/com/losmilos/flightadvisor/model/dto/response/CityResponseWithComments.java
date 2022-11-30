package com.losmilos.flightadvisor.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class CityResponseWithComments {

    private Long id;

    private String name;

    private String country;

    private String description;

    private List<CommentResponse> comments;
}

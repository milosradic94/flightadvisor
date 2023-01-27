package com.losmilos.flightadvisor.model.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class CityResponseWithComments implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String country;

    private String description;

    private List<CommentResponse> comments;
}

package com.losmilos.flightadvisor.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter @Setter
public class UpdateCommentRequest {

    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 2, max = 2000)
    private String description;

    @NotNull
    private Long cityId;
}

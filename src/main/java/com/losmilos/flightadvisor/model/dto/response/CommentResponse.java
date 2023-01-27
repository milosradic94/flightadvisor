package com.losmilos.flightadvisor.model.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class CommentResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String description;

    private Long userId;

    private Long cityId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

package com.losmilos.flightadvisor.model.dto.messaging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDataMessage {

    private String id;

    private String description;

}

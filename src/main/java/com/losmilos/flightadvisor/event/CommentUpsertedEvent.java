package com.losmilos.flightadvisor.event;

import com.losmilos.flightadvisor.model.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentUpsertedEvent {

    private final Comment comment;

}

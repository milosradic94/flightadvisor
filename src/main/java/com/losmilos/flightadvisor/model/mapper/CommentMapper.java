package com.losmilos.flightadvisor.model.mapper;

import com.losmilos.flightadvisor.model.domain.Comment;
import com.losmilos.flightadvisor.model.dto.messaging.CommentDataMessage;
import com.losmilos.flightadvisor.model.dto.response.CommentResponse;
import com.losmilos.flightadvisor.model.persistance.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {

    CommentEntity domainToEntity(Comment comment);

    Comment entityToDomain(CommentEntity commentEntity);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "city.id", target = "cityId")
    CommentResponse domainToDto(Comment comment);

    CommentDataMessage domainToMessage(Comment comment);

    Comment messageToDomain(CommentDataMessage commentDataMessage);
}

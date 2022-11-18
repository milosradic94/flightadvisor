package com.losmilos.flightadvisor.model.mapper;

import com.losmilos.flightadvisor.model.dto.request.AddCommentRequest;
import com.losmilos.flightadvisor.model.dto.response.CommentResponse;
import com.losmilos.flightadvisor.model.persistance.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {

    @Mapping(source = "cityId", target = "city.id")
    CommentEntity dtoToEntity(AddCommentRequest addCommentRequest);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "city.id", target = "cityId")
    CommentResponse entityToDto(CommentEntity commentEntity);
}

package com.losmilos.flightadvisor.model.mapper;

import com.losmilos.flightadvisor.model.domain.City;
import com.losmilos.flightadvisor.model.dto.request.CityRequest;
import com.losmilos.flightadvisor.model.dto.response.CityResponse;
import com.losmilos.flightadvisor.model.dto.response.CityResponseWithComments;
import com.losmilos.flightadvisor.model.dto.response.CommentResponse;
import com.losmilos.flightadvisor.model.persistance.CityEntity;
import com.losmilos.flightadvisor.model.persistance.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CityMapper {

    City dtoToDomain(CityRequest cityRequest);

    CityEntity domainToEntity(City city);

    City entityToDomain(CityEntity city);

    CityResponse domainToDto(City city);

    CityResponseWithComments entityToResponse(CityEntity cityEntity);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "city.id", target = "cityId")
    CommentResponse commentEntityToCommentResponse(CommentEntity commentEntity);
}

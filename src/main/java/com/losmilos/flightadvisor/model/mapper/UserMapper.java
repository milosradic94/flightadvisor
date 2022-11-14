package com.losmilos.flightadvisor.model.mapper;

import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.dto.response.UserResponse;
import com.losmilos.flightadvisor.model.persistance.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "role", expression = "java(user.getAuthorities().stream().findFirst().toString())")
    User entityToDomain(UserEntity user);

    UserResponse domainToResponse(User user);
}

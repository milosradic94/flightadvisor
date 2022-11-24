package com.losmilos.flightadvisor.model.mapper;

import com.losmilos.flightadvisor.model.domain.Role;
import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.dto.response.UserResponse;
import com.losmilos.flightadvisor.model.persistance.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface UserMapper {

    User entityToDomain(UserEntity user);

    @Mapping(target = "role", qualifiedByName = "getRoleName")
    UserResponse domainToResponse(User user);

    @Named("getRoleName")
    default String getRoleName(Role role) {
        return role.getRole().name();
    }
}

package com.losmilos.flightadvisor.model.mapper;

import com.losmilos.flightadvisor.model.domain.Signup;
import com.losmilos.flightadvisor.model.dto.request.SignupRequest;
import com.losmilos.flightadvisor.model.persistance.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PasswordEncoderMapper.class)
public interface SignupMapper {

    Signup dtoToDomain(SignupRequest signupRequest);

    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    UserEntity domainToEntity(Signup signup);
}

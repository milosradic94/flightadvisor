package com.losmilos.flightadvisor.model.mapper;

import com.losmilos.flightadvisor.model.domain.Signin;
import com.losmilos.flightadvisor.model.dto.request.SigninRequest;
import org.mapstruct.Mapper;

@Mapper
public interface SigninMapper {

    Signin dtoToDomain(SigninRequest signinRequest);
}

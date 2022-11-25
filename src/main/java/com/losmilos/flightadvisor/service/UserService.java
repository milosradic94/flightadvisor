package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.exception.NotFoundException;
import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.mapper.UserMapperImpl;
import com.losmilos.flightadvisor.model.persistance.UserEntity;
import com.losmilos.flightadvisor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapperImpl userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User Not Found!"));

        return User.build(userMapper.entityToDomain(userEntity));
    }
}

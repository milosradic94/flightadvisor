package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.exception.NotFoundException;
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

    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User Not Found."));

        return UserEntity.build(userEntity);
    }
}

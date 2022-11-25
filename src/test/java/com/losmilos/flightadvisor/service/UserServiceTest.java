package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.exception.NotFoundException;
import com.losmilos.flightadvisor.model.domain.Role;
import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.mapper.UserMapperImpl;
import com.losmilos.flightadvisor.model.persistance.RoleEntity;
import com.losmilos.flightadvisor.model.persistance.UserEntity;
import com.losmilos.flightadvisor.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapperImpl userMapper;

    @InjectMocks
    private UserService userService;

    private static final String USERNAME = "DummyUsername";

    private static final Role ROLE = Role.builder()
            .id(1l)
            .role(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)
            .build();

    private static final User USER = User.builder()
            .id(1l)
            .username("DummyUsername")
            .password("DummyPassword")
            .role(ROLE)
            .authorities(Collections.singletonList(new SimpleGrantedAuthority(ROLE.getRole().name())))
            .build();

    private static final UserDetails USER_DETAILS = USER;

    private static final RoleEntity ROLE_ENTITY = RoleEntity.builder()
            .id(1l)
            .role(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)
            .build();

    private static final UserEntity USER_ENTITY = UserEntity.builder()
            .id(1l)
            .firstName("DummyFirstName")
            .lastName("DummyLastName")
            .username("DummyUsername")
            .password("DummyPassword")
            .role(ROLE_ENTITY)
            .build();

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUsernameDoesntExist() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> userService.loadUserByUsername(USERNAME));

        Assertions.assertEquals(notFoundException.getMessage(), "User Not Found!");
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUsernameExists() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(USER_ENTITY));
        when(userMapper.entityToDomain(USER_ENTITY)).thenReturn(USER);

        final var userDetails = userService.loadUserByUsername(USERNAME);

        Assertions.assertEquals(USER_DETAILS, userDetails);
    }
}

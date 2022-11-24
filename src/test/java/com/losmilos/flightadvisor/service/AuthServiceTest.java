package com.losmilos.flightadvisor.service;

import static org.mockito.Mockito.when;

import com.losmilos.flightadvisor.exception.NotFoundException;
import com.losmilos.flightadvisor.model.domain.Role;
import com.losmilos.flightadvisor.model.domain.Signin;
import com.losmilos.flightadvisor.model.domain.Signup;
import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.dto.response.MessageResponse;
import com.losmilos.flightadvisor.model.dto.response.UserResponse;
import com.losmilos.flightadvisor.model.mapper.SignupMapperImpl;
import com.losmilos.flightadvisor.model.mapper.UserMapperImpl;
import com.losmilos.flightadvisor.model.persistance.RoleEntity;
import com.losmilos.flightadvisor.model.persistance.UserEntity;
import com.losmilos.flightadvisor.repository.RoleRepository;
import com.losmilos.flightadvisor.repository.UserRepository;
import com.losmilos.flightadvisor.security.jwt.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private SignupMapperImpl signupMapper;

    @Mock
    private UserMapperImpl userMapper;

    @InjectMocks
    private AuthService authService;

    @Test
    void authenticateUser_ShouldReturnResponseEntityOk_WhenUserIsSuccessfullyAuthenticated() {
        final var role = Role.builder()
                .id(1l)
                .role(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)
                .build();

        final var user = User.builder()
                .id(1l)
                .username("DummyUsername")
                .password("DummyPassword")
                .role(role)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(role.getRole().name())))
                .build();

        final var signin = Signin.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        final var cookie = ResponseCookie.from("DummyCookie", UUID.randomUUID().toString()).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();

        final var userResponse = UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .role(user.getRole().getRole().name())
                        .build();

        when(userMapper.domainToResponse(user)).thenReturn(userResponse);
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signin.getUsername(), signin.getPassword()))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtUtils.generateJwtCookie(user)).thenReturn(cookie);

        final var responseEntity = authService.authenticateUser(signin);

        Assertions.assertEquals(cookie.toString(), responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(userResponse, responseEntity.getBody());
    }

    @Test
    void registerUser_ShouldReturnResponseEntityBadRequest_WhenUserAlreadyExists() {
        final var body = new MessageResponse("Error: Username is already taken!");

        final var role = RoleEntity.builder()
                .id(1l)
                .role(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)
                .build();

        final var signup = Signup.builder()
                .firstName("DummyFirstName")
                .lastName("DummyLastName")
                .username("DummyUsername")
                .password("DummyPassword")
                .build();

        final var user = UserEntity.builder()
                .id(1l)
                .firstName("DummyFirstName")
                .lastName("DummyLastName")
                .username("DummyUsername")
                .password("DummyPassword")
                .role(role)
                .build();


        when(signupMapper.domainToEntity(signup)).thenReturn(user);
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        final var responseEntity = authService.registerUser(signup);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(body.getMessage(), responseEntity.getBody().getMessage());
    }

    @Test
    void registerUser_ShouldThrowNotFoundException_WhenRoleDoesntExist() {
        final var role = RoleEntity.builder()
                .id(1l)
                .role(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)
                .build();

        final var signup = Signup.builder()
                .firstName("DummyFirstName")
                .lastName("DummyLastName")
                .username("DummyUsername")
                .password("DummyPassword")
                .build();

        final var user = UserEntity.builder()
                .id(1l)
                .firstName("DummyFirstName")
                .lastName("DummyLastName")
                .username("DummyUsername")
                .password("DummyPassword")
                .role(role)
                .build();


        when(signupMapper.domainToEntity(signup)).thenReturn(user);
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(roleRepository.findByRole(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)).thenThrow(new NotFoundException("Role Not Found."));

        Assertions.assertThrows(NotFoundException.class, () -> authService.registerUser(signup));
    }

    @Test
    void registerUser_ShouldReturnResponseEntityOk_WhenUserIsSuccessfullyRegistered() {
        final var body = new MessageResponse("User registered successfully!");

        final var role = RoleEntity.builder()
                .id(1l)
                .role(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)
                .build();

        final var signup = Signup.builder()
                .firstName("DummyFirstName")
                .lastName("DummyLastName")
                .username("DummyUsername")
                .password("DummyPassword")
                .build();

        final var user = UserEntity.builder()
                .id(1l)
                .firstName("DummyFirstName")
                .lastName("DummyLastName")
                .username("DummyUsername")
                .password("DummyPassword")
                .role(role)
                .build();


        when(signupMapper.domainToEntity(signup)).thenReturn(user);
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(roleRepository.findByRole(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)).thenReturn(Optional.of(role));

        final var responseEntity = authService.registerUser(signup);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(body.getMessage(), responseEntity.getBody().getMessage());
    }

    @Test
    void logoutUser_ShouldReturnResponseEntityOk_WhenUserIsSuccessfullyLoggedOut() {
        final var body = new MessageResponse("You've been signed out!");
        final var cookie = ResponseCookie.from("DummyCookie", null).path("/api").httpOnly(true).build();

        when(jwtUtils.getCleanJwtCookie()).thenReturn(cookie);

        final var responseEntity = authService.logoutUser();

        Assertions.assertEquals(cookie.toString(), responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(body.getMessage(), responseEntity.getBody().getMessage());
    }
}

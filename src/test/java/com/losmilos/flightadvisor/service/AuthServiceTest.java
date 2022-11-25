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

    private static final Signin SIGNIN = Signin.builder()
            .username(USER.getUsername())
            .password(USER.getPassword())
            .build();

    private static final UserResponse USER_RESPONSE = UserResponse.builder()
            .id(USER.getId())
            .username(USER.getUsername())
            .role(USER.getRole().getRole().name())
            .build();

    private static final RoleEntity ROLE_ENTITY = RoleEntity.builder()
            .id(1l)
            .role(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)
            .build();

    private static final Signup SIGNUP = Signup.builder()
            .firstName("DummyFirstName")
            .lastName("DummyLastName")
            .username("DummyUsername")
            .password("DummyPassword")
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
    void authenticateUser_ShouldReturnResponseEntityOk_WhenUserIsSuccessfullyAuthenticated() {
        final var cookie = ResponseCookie.from("DummyCookie", UUID.randomUUID().toString()).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();

        when(userMapper.domainToResponse(USER)).thenReturn(USER_RESPONSE);
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(SIGNIN.getUsername(), SIGNIN.getPassword()))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(USER);
        when(jwtUtils.generateJwtCookie(USER)).thenReturn(cookie);

        final var responseEntity = authService.authenticateUser(SIGNIN);

        Assertions.assertEquals(cookie.toString(), responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(USER_RESPONSE, responseEntity.getBody());
    }

    @Test
    void registerUser_ShouldReturnResponseEntityBadRequest_WhenUserAlreadyExists() {
        final var body = new MessageResponse("Error: Username is already taken!");

        when(signupMapper.domainToEntity(SIGNUP)).thenReturn(USER_ENTITY);
        when(userRepository.existsByUsername(USER_ENTITY.getUsername())).thenReturn(true);

        final var responseEntity = authService.registerUser(SIGNUP);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(body.getMessage(), responseEntity.getBody().getMessage());
    }

    @Test
    void registerUser_ShouldThrowNotFoundException_WhenRoleDoesntExist() {
        when(signupMapper.domainToEntity(SIGNUP)).thenReturn(USER_ENTITY);
        when(userRepository.existsByUsername(USER_ENTITY.getUsername())).thenReturn(false);
        when(roleRepository.findByRole(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)).thenReturn(Optional.empty());

        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> authService.registerUser(SIGNUP));

        Assertions.assertEquals(notFoundException.getMessage(), "Role Not Found!");
    }

    @Test
    void registerUser_ShouldReturnResponseEntityOk_WhenUserIsSuccessfullyRegistered() {
        final var body = new MessageResponse("User registered successfully!");

        when(signupMapper.domainToEntity(SIGNUP)).thenReturn(USER_ENTITY);
        when(userRepository.existsByUsername(USER_ENTITY.getUsername())).thenReturn(false);
        when(roleRepository.findByRole(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)).thenReturn(Optional.of(ROLE_ENTITY));

        final var responseEntity = authService.registerUser(SIGNUP);

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

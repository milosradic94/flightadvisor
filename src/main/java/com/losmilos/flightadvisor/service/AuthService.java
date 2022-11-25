package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.enumeration.Role;
import com.losmilos.flightadvisor.exception.NotFoundException;
import com.losmilos.flightadvisor.model.domain.Signin;
import com.losmilos.flightadvisor.model.domain.Signup;
import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.dto.response.MessageResponse;
import com.losmilos.flightadvisor.model.dto.response.UserResponse;
import com.losmilos.flightadvisor.model.mapper.SignupMapperImpl;
import com.losmilos.flightadvisor.model.mapper.UserMapperImpl;
import com.losmilos.flightadvisor.model.persistance.UserEntity;
import com.losmilos.flightadvisor.repository.RoleRepository;
import com.losmilos.flightadvisor.repository.UserRepository;
import com.losmilos.flightadvisor.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final JwtUtils jwtUtils;

    private final SignupMapperImpl signupMapper;

    private final UserMapperImpl userMapper;

    public ResponseEntity<UserResponse> authenticateUser(Signin signin) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signin.getUsername(), signin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtUtils.generateJwtCookie(user).toString())
                .body(userMapper.domainToResponse(user));
    }

    public ResponseEntity<MessageResponse> registerUser(Signup signup) throws NotFoundException {
        UserEntity user = signupMapper.domainToEntity(signup);

        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        user.setRole(roleRepository.findByRole(Role.ROLE_USER).orElseThrow(() -> new NotFoundException("Role Not Found!")));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<MessageResponse> logoutUser() {
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtUtils.getCleanJwtCookie().toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}

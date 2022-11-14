package com.losmilos.flightadvisor.api.v1;

import javax.validation.Valid;
import com.losmilos.flightadvisor.model.dto.request.SigninRequest;
import com.losmilos.flightadvisor.model.dto.request.SignupRequest;
import com.losmilos.flightadvisor.model.dto.response.MessageResponse;
import com.losmilos.flightadvisor.model.dto.response.UserResponse;
import com.losmilos.flightadvisor.model.mapper.SigninMapperImpl;
import com.losmilos.flightadvisor.model.mapper.SignupMapperImpl;
import com.losmilos.flightadvisor.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final SignupMapperImpl signupMapper;

    private final SigninMapperImpl signinMapper;

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> authenticateUser(@Valid @RequestBody SigninRequest signinRequest) {
        return authService.authenticateUser(signinMapper.dtoToDomain(signinRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return authService.registerUser(signupMapper.dtoToDomain(signupRequest));
    }

    @PostMapping("/signout")
    public ResponseEntity<MessageResponse> logoutUser() {
        return authService.logoutUser();
    }
}

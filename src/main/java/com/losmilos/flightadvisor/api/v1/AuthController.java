package com.losmilos.flightadvisor.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "Endpoints for authentication")
public class AuthController {

    private final AuthService authService;

    private final SignupMapperImpl signupMapper;

    private final SigninMapperImpl signinMapper;

    @PostMapping("/signin")
    @Operation(summary = "Sign in")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a user",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = UserResponse.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Bad credentials",
            content = @Content
        )
    })
    public ResponseEntity<UserResponse> authenticateUser(@Valid @RequestBody SigninRequest signinRequest) {
        return authService.authenticateUser(signinMapper.dtoToDomain(signinRequest));
    }

    @PostMapping("/signup")
    @Operation(summary = "Sign up")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a message",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = MessageResponse.class
                )
            )
        )
    })
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return authService.registerUser(signupMapper.dtoToDomain(signupRequest));
    }

    @PostMapping("/signout")
    @Operation(summary = "Sign out")
    @ApiResponse(
        responseCode = "200",
        description = "Returns a message",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = MessageResponse.class
            )
        )
    )
    public ResponseEntity<MessageResponse> logoutUser() {
        return authService.logoutUser();
    }
}

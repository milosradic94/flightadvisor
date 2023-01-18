package com.losmilos.flightadvisor.api.v1;

import com.losmilos.flightadvisor.model.dto.response.MessageResponse;
import com.losmilos.flightadvisor.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/airport")
@Tag(name = "Airports", description = "Endpoints for airports")
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Import airports CSV or TXT file")
    @ApiResponse(
        responseCode = "201",
        description = "Returns a message",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = MessageResponse.class
            )
        )
    )
    public ResponseEntity<MessageResponse> importAirports(@RequestParam("file") MultipartFile file) {
        airportService.importCsv(file);
        return new ResponseEntity<MessageResponse>(new MessageResponse("CSV will be parsed in background!"), HttpStatus.CREATED);
    }
}

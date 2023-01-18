package com.losmilos.flightadvisor.api.v1;

import com.losmilos.flightadvisor.model.dto.response.CheapestFlightResponse;
import com.losmilos.flightadvisor.model.dto.response.MessageResponse;
import com.losmilos.flightadvisor.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/route")
@Tag(name = "Routes", description = "Endpoints for routes")
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    @Operation(summary = "Get cheapest flights from source to destination city")
    @ApiResponse(
        responseCode = "200",
        description = "Returns a list of routes with total cost and number of routes included",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = CheapestFlightResponse.class
            )
        )
    )
    public ResponseEntity<CheapestFlightResponse> getCheapestFlight(@RequestParam Long sourceCityId, @RequestParam Long destinationCityId)
    {
        return ResponseEntity.ok(routeService.findCheapestFlight(sourceCityId, destinationCityId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Import routes CSV or TXT file")
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
    public ResponseEntity<MessageResponse> importRoutes(@RequestParam("file") MultipartFile file) {
        routeService.importCsv(file);
        return new ResponseEntity<MessageResponse>(new MessageResponse("CSV will be parsed in background!"), HttpStatus.CREATED);
    }
}

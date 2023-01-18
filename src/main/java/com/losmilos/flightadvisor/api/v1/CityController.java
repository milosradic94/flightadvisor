package com.losmilos.flightadvisor.api.v1;

import com.losmilos.flightadvisor.model.domain.City;
import com.losmilos.flightadvisor.model.dto.request.CityRequest;
import com.losmilos.flightadvisor.model.dto.response.CityResponse;
import com.losmilos.flightadvisor.model.dto.response.CityResponseWithComments;
import com.losmilos.flightadvisor.model.mapper.CityMapperImpl;
import com.losmilos.flightadvisor.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/city")
@Tag(name = "Cities", description = "Endpoints for cities")
public class CityController {

    private final CityService cityService;

    private final CityMapperImpl cityMapper;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create city")
    @ApiResponse(
        responseCode = "201",
        description = "City is created",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = CityResponse.class
            )
        )
    )
    public ResponseEntity<CityResponse> addCity(@Valid @RequestBody CityRequest cityRequest) {
        City city = cityService.addCity(cityMapper.dtoToDomain(cityRequest));

        return new ResponseEntity<CityResponse>(cityMapper.domainToDto(city), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get/Search a list of cities with comments")
    @ApiResponse(
        responseCode = "200",
        description = "Returns a list of cities with comments",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(
                schema = @Schema(
                    implementation = CityResponseWithComments.class
                )
            )
        )
    )
    public ResponseEntity<List<CityResponseWithComments>> getCities(@RequestParam(required = false) String searchByName, @RequestParam(required = false, value = "numberOfComments", defaultValue = Integer.MAX_VALUE + "") Integer numberOfComments) {
        final var cities = cityService.getCities(searchByName, numberOfComments);

        return ResponseEntity.ok(cities);
    }
}

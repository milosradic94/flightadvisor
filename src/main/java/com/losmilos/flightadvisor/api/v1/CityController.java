package com.losmilos.flightadvisor.api.v1;

import com.losmilos.flightadvisor.model.domain.City;
import com.losmilos.flightadvisor.model.dto.request.CityRequest;
import com.losmilos.flightadvisor.model.dto.response.CityResponse;
import com.losmilos.flightadvisor.model.dto.response.CityResponseWithComments;
import com.losmilos.flightadvisor.model.mapper.CityMapperImpl;
import com.losmilos.flightadvisor.service.CityService;
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
public class CityController {

    private final CityService cityService;

    private final CityMapperImpl cityMapper;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CityResponse> addCity(@Valid @RequestBody CityRequest cityRequest) {
        City city = cityService.addCity(cityMapper.dtoToDomain(cityRequest));

        return new ResponseEntity<CityResponse>(cityMapper.domainToDto(city), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CityResponseWithComments>> getCities(@RequestParam(required = false) String searchByName, @RequestParam(required = false, value = "numberOfComments", defaultValue = Integer.MAX_VALUE + "") Integer numberOfComments) {
        final var cities = cityService.getCities(searchByName, numberOfComments);

        return ResponseEntity.ok(cities);
    }
}

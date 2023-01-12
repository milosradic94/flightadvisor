package com.losmilos.flightadvisor.api.v1;

import com.losmilos.flightadvisor.model.dto.response.MessageResponse;
import com.losmilos.flightadvisor.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/route")
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<?> getCheapestFlight(@RequestParam Long sourceCityId, @RequestParam Long destinationCityId)
    {
        return routeService.findCheapestFlight(sourceCityId, destinationCityId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MessageResponse> importRoutes(@RequestParam("file") MultipartFile file) {
        routeService.importCsv(file);
        return new ResponseEntity<MessageResponse>(new MessageResponse("CSV will be parsed in background!"), HttpStatus.CREATED);
    }
}

package com.losmilos.flightadvisor.api.v1;

import com.losmilos.flightadvisor.model.dto.response.MessageResponse;
import com.losmilos.flightadvisor.service.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/airport")
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MessageResponse> importAirports(@RequestParam("file") MultipartFile file) throws IOException {
        airportService.importCsv(file);
        return new ResponseEntity<MessageResponse>(new MessageResponse("CSV will be parsed in background!"), HttpStatus.CREATED);
    }
}

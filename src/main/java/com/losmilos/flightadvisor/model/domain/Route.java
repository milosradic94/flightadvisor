package com.losmilos.flightadvisor.model.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Setter;

@Setter
public class Route {

    @CsvBindByPosition(position = 0)
    private String airline;

    @CsvBindByPosition(position = 1)
    private String airlineId;

    @CsvBindByPosition(position = 2)
    private String sourceAirport;

    @CsvBindByPosition(position = 3)
    private String sourceAirportId;

    @CsvBindByPosition(position = 4)
    private String destinationAirport;

    @CsvBindByPosition(position = 5)
    private String destinationAirportId;

    @CsvBindByPosition(position = 6)
    private String codeshare;

    @CsvBindByPosition(position = 7)
    private String stops;

    @CsvBindByPosition(position = 8)
    private String equipment;

    @CsvBindByPosition(position = 9)
    private Double price;

    public String getAirline() {
        return airline;
    }

    public Long getAirlineId() {
        return isValid(airlineId) ? Long.valueOf(airlineId) : 0L;
    }

    public String getSourceAirport() {
        return sourceAirport;
    }

    public Long getSourceAirportId() {
        return isValid(sourceAirportId) ? Long.valueOf(sourceAirportId) : 0L;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public Long getDestinationAirportId() {
        return isValid(destinationAirportId) ? Long.valueOf(destinationAirportId) : 0L;
    }

    public String getCodeshare() {
        return isValid(codeshare) ? codeshare : null;
    }

    public Long getStops() {
        return isValid(stops) ? Long.valueOf(stops) : 0L;
    }

    public String getEquipment() {
        return isValid(equipment) ? equipment : null;
    }

    public Double getPrice() {
        return price;
    }

    public boolean isValid(String value) {
        return value != null &&
                !value.isBlank() &&
                !value.trim().equals("N");
    }
}
